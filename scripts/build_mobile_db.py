#!/usr/bin/env python3
"""
Hacking-Cheatsheets Mobile Database Builder
Parses all Markdown cheatsheets (English, Turkish, Italian) and compiles
them into an optimized, pre-indexed SQLite database with FTS5 search.
"""

import os
import re
import json
import sqlite3

CATEGORY_MAPPING = {
    "Attack-Methodology": "Attack Methodology",
    "AD-Attack-Methodology": "Active Directory",
    "BloodHound": "Active Directory",
    "Certipy": "Active Directory",
    "CrackMapExec": "Active Directory",
    "Evil-WinRM": "Active Directory",
    "Impacket": "Active Directory",
    "Kerbrute": "Active Directory",
    "NetExec": "Active Directory",
    "PowerView": "Active Directory",
    "Responder": "Active Directory",
    "Rubeus": "Active Directory",
    "Ligolo-ng": "Pivoting & Tunneling",
    "Chisel": "Pivoting & Tunneling",
    "Linux-PrivEsc": "Privilege Escalation",
    "Windows-PrivEsc": "Privilege Escalation",
    "PEASS-ng": "Privilege Escalation",
    "LOLBAS-GTFOBins": "Privilege Escalation",
    "Mimikatz": "Privilege Escalation",
    "Nmap": "Recon & Scanning",
    "RustScan": "Recon & Scanning",
    "Masscan": "Recon & Scanning",
    "dnsx": "Recon & Scanning",
    "Feroxbuster": "Recon & Scanning",
    "Dirb": "Recon & Scanning",
    "dirsearch": "Recon & Scanning",
    "Gobuster": "Recon & Scanning",
    "Nikto": "Recon & Scanning",
    "ffuf": "Recon & Scanning",
    "Nuclei": "Recon & Scanning",
    "Subfinder": "Recon & Scanning",
    "httpx": "Recon & Scanning",
    "Katana": "Recon & Scanning",
    "GAU": "Recon & Scanning",
    "Amass": "Recon & Scanning",
    "Arjun": "Recon & Scanning",
    "Dalfox": "Recon & Scanning",
    "Burp-Suite": "Web Application Testing",
    "OWASP-ZAP": "Web Application Testing",
    "SQLMap": "Web Application Testing",
    "WPScan": "Web Security & API",
    "Kiterunner": "Web Security & API",
    "Wfuzz": "Web Security & API",
    "API-Security": "Web Security & API",
    "Auth-Bypass": "Web Security & API",
    "Cache-Poisoning": "Web Security & API",
    "Cloudflare-Bypass": "Web Security & API",
    "CORS": "Web Security & API",
    "HTTP-Request-Smuggling": "Web Security & API",
    "IDOR": "Web Security & API",
    "JWT-Attacks": "Web Security & API",
    "Open-Redirect": "Web Security & API",
    "Prototype-Pollution": "Web Security & API",
    "Race-Conditions": "Web Security & API",
    "SSRF": "Web Security & API",
    "Subdomain-Takeover": "Web Security & API",
    "WAF-Bypass": "Web Security & API",
    "XXE": "Web Security & API",
    "Payloads": "Payloads Collection",
    "Metasploit": "Exploitation & Shells",
    "PowerShell": "Exploitation & Shells",
    "Linux-Commands": "Exploitation & Shells",
    "rlwrap": "Exploitation & Shells",
    "Netcat": "Exploitation & Shells",
    "Socat": "Exploitation & Shells",
    "Hydra": "Password Cracking",
    "John-The-Ripper": "Password Cracking",
    "Hashcat": "Password Cracking",
    "CeWL": "Password Cracking",
    "Crunch": "Password Cracking",
    "Wireshark": "Network Analysis",
    "tcpdump": "Network Analysis",
    "tshark": "Network Analysis",
    "Aircrack-ng": "Wireless & Network",
    "Bettercap": "Wireless & Network",
    "Wifite": "Wireless & Network",
    "Network-Pentesting": "Wireless & Network",
    "Google-Dorking": "OSINT & Dorking",
    "GitHub-Dorking": "OSINT & Dorking",
    "Shodan": "OSINT & Dorking",
    "OSINT": "OSINT & Dorking",
    "TruffleHog": "OSINT & Dorking",
    "Social-Engineering": "Social Engineering",
    "Ligolo-ng": "Pivoting & Tunneling",
    "Chisel": "Pivoting & Tunneling",
    "Proxychains": "Pivoting & Tunneling",
    "sshuttle": "Pivoting & Tunneling",
    "BloodHound": "Active Directory & Network",
    "Certipy": "Active Directory & Network",
    "CrackMapExec": "Active Directory & Network",
    "Evil-WinRM": "Active Directory & Network",
    "Impacket": "Active Directory & Network",
    "Kerbrute": "Active Directory & Network",
    "NetExec": "Active Directory & Network",
    "PowerView": "Active Directory & Network",
    "Responder": "Active Directory & Network",
    "Rubeus": "Active Directory & Network",
    "Snaffler": "Active Directory & Network",
    "Coercer": "Active Directory & Network",
    "PKINITtools": "Active Directory & Network",
    "GDB": "Reverse Engineering",
    "Ghidra": "Reverse Engineering",
    "x64dbg": "Reverse Engineering",
    "Radare2": "Reverse Engineering",
    "strace-ltrace": "Reverse Engineering",
    "pwndbg-GEF": "Reverse Engineering",
    "Ropper": "Reverse Engineering",
    "checksec": "Reverse Engineering",
    "Autopsy": "Digital Forensics",
    "Binwalk": "Digital Forensics",
    "ExifTool": "Digital Forensics",
    "Volatility": "Digital Forensics",
    "Cloud-Security": "Cloud Security",
    "Pacu": "Cloud Security",
    "Prowler": "Cloud Security",
    "Container-Security": "Container Security",
    "IoT-Hacking": "IoT & Hardware",
    "Mobile-Security": "Mobile Security",
    "Frida": "Mobile Security",
    "Jadx": "Mobile Security",
    "Commix": "Web Security & API",
    "Tplmap": "Web Security & API",
    "WhatWeb": "Recon & Scanning",
    "Naabu": "Recon & Scanning",
    "Gitleaks": "OSINT & Dorking",
    "SSH-Tunneling": "Pivoting & Tunneling",
    "adidnsdump": "Active Directory & Network",
    "Msfvenom": "Exploitation & Shells",
    "Sliver": "Exploitation & Shells",
    "Blue-Team": "Blue Team & Defense",
    "CTF": "CTF Cheatsheets",
    "Reporting": "Reporting & Methodologies",
    "Bug-Bounty-Methodology": "Reporting & Methodologies",
    "Wordlists": "Reference Guides",
    "Kali-Linux-Tools": "Reference Guides",
    "jwt_tool": "Web Security & API",
    "GitTools": "OSINT & Dorking",
    "smuggler": "Web Security & API",
    "CORSy": "Web Security & API",
    "InQL": "Web Security & API",
    "mitmproxy": "Web Application Testing",
    "Scapy": "Network Analysis",
    "Ettercap": "Network Analysis",
    "Yersinia": "Network Analysis",
    "name-that-hash": "Password Cracking",
    "LaZagne": "Privilege Escalation",
    "CUPP": "Password Cracking",
    "fcrackzip": "Password Cracking",
    "theHarvester": "OSINT & Dorking",
    "Sherlock": "OSINT & Dorking",
    "dnsrecon": "Recon & Scanning",
    "hcxdumptool": "Wireless & Network",
    "Reaver": "Wireless & Network",
    "Trivy": "Container Security",
    "kube-bench": "Container Security",
    "Searchsploit": "Exploitation & Shells",
    "enum4linux-ng": "Active Directory & Network",
    "pwntools": "Reverse Engineering",
    "pypykatz": "Privilege Escalation",
    "wafw00f": "Web Security & API",
}

def extract_title(content, fallback):
    for line in content.splitlines():
        line = line.strip()
        if line.startswith("# "):
            title = line[2:].strip()
            # Clean emojis or decorative symbols if needed
            title = re.sub(r"^[^\w\s\(\)\-\.]+\s*", "", title)
            return title
    return fallback

def extract_description(content):
    # Search for blockquotes or introductory bold sentences
    match = re.search(r">\s*\*\*?(.*?)\*\*?", content)
    if match:
        desc = match.group(1).strip()
        if len(desc) > 10:
            return desc[:200]
    # Search for <b>...</b>
    match2 = re.search(r"<b>(.*?)</b>", content)
    if match2:
        return match2.group(1).strip()[:200]
    return "Complete penetration testing cheatsheet and command reference."

def clean_command(line):
    line = line.strip()
    if not line: return None
    # Strip leading shell prompt $ or #
    if line.startswith("$ ") or line.startswith("# "):
        line = line[2:].strip()
    # Strip trailing comments e.g. "nmap -h  # Help"
    line = re.sub(r"\s+#\s+.*$", "", line).strip()
    return line

def is_valid_quick_command(cmd):
    if not cmd or len(cmd) < 3 or len(cmd) > 200:
        return False
    # No ascii art or box diagrams
    if any(c in "█═╔╗║╚╝▀▄▌▐░▒▓│┌┐└┘┼" for c in cmd):
        return False
    if re.search(r"[/\\_]{4,}", cmd):
        return False
    if cmd.startswith("+--") or "+---+" in cmd:
        return False
    # No emojis
    if re.search(r"[\U00010000-\U0010ffff\u2600-\u26ff\u2700-\u27bf]", cmd):
        return False
    # No comment lines or markdown syntax
    if cmd.startswith(("#", "//", "::", "rem", "/*", "<!--", "<", ">", "|", "[", "{", "}", "(", ")", "- ", "* ", "+ ")):
        return False
    # No numbered list descriptions (e.g. "1. From attacker...")
    if re.match(r"^\d+[\.\)]\s+", cmd):
        return False
    # No pure URLs
    if cmd.startswith("http://") or cmd.startswith("https://"):
        return False
    # Check first word
    words = cmd.split()
    first_word = words[0]
    if first_word[0].isupper():
        known_uppers = ("Get-", "Invoke-", "New-", "Set-", "Select-", "Start-", "Stop-", "Test-", "BloodHound", "Certipy", "Chisel", "Mimikatz", "Rubeus", "PowerView", "Net", "Cmd")
        if not any(first_word.startswith(k) for k in known_uppers):
            return False
    # Exclude slogans/sentences: must have command flags (- or -- or /) or file extensions or pipes/operators
    if not re.search(r"(\s-[a-zA-Z]|\s--[a-zA-Z]|\s/|/bin/|/etc/|/usr/|\||&&|;|\.py|\.sh|\.exe|\.txt|\.raw)", cmd):
        if cmd.lower() not in ("whoami", "id", "hostname", "pwd", "ifconfig", "ip a", "route -n", "arp -a", "netstat -ano"):
            return False
    return True

def extract_quick_commands(content, tool_name=""):
    blocks = re.findall(r"^[ \t]*```[a-zA-Z0-9_-]*\s*\n(.*?)\n[ \t]*```", content, re.MULTILINE | re.DOTALL)
    tool_tokens = [tool_name.lower().replace("-", ""), tool_name.lower()]
    t_lower = tool_name.lower()
    if "netexec" in t_lower: tool_tokens.extend(["nxc"])
    elif "ligolo" in t_lower: tool_tokens.extend(["proxy", "agent", "./proxy", "./agent"])
    elif "peass" in t_lower: tool_tokens.extend(["linpeas", "winpeas"])
    elif "john" in t_lower: tool_tokens.extend(["john", "./john"])
    elif "hashcat" in t_lower: tool_tokens.extend(["hashcat", "./hashcat", "hashcat.exe"])
    elif "crackmap" in t_lower: tool_tokens.extend(["crackmapexec", "cme"])
    elif "dirsearch" in t_lower: tool_tokens.extend(["dirsearch", "python3 dirsearch.py"])
    elif "tshark" in t_lower: tool_tokens.extend(["tshark"])
    elif "rlwrap" in t_lower: tool_tokens.extend(["rlwrap", "nc", "stty"])
    elif "netcat" in t_lower or t_lower == "nc": tool_tokens.extend(["nc", "ncat", "netcat", "nc.openbsd", "nc.traditional"])
    elif "socat" in t_lower: tool_tokens.extend(["socat"])
    elif "proxychains" in t_lower: tool_tokens.extend(["proxychains", "proxychains4"])
    elif "sshuttle" in t_lower: tool_tokens.extend(["sshuttle"])
    elif "rustscan" in t_lower: tool_tokens.extend(["rustscan"])
    elif "masscan" in t_lower: tool_tokens.extend(["masscan"])
    elif "wpscan" in t_lower: tool_tokens.extend(["wpscan"])
    elif "kiterunner" in t_lower: tool_tokens.extend(["kr", "kiterunner"])
    elif "wfuzz" in t_lower: tool_tokens.extend(["wfuzz"])
    elif "dnsx" in t_lower: tool_tokens.extend(["dnsx"])
    elif "snaffler" in t_lower: tool_tokens.extend(["snaffler", "snaffler.exe"])
    elif "coercer" in t_lower: tool_tokens.extend(["coercer", "python3 coercer.py"])
    elif "pkinit" in t_lower: tool_tokens.extend(["gettgtpkinit.py", "getskeyhash.py", "gettgtpkinit", "getskeyhash"])
    elif "cewl" in t_lower: tool_tokens.extend(["cewl"])
    elif "crunch" in t_lower: tool_tokens.extend(["crunch"])
    elif "trufflehog" in t_lower: tool_tokens.extend(["trufflehog"])
    elif "commix" in t_lower: tool_tokens.extend(["commix", "python commix.py", "python3 commix.py"])
    elif "tplmap" in t_lower: tool_tokens.extend(["tplmap", "python tplmap.py", "python3 tplmap.py"])
    elif "whatweb" in t_lower: tool_tokens.extend(["whatweb"])
    elif "adidnsdump" in t_lower: tool_tokens.extend(["adidnsdump"])
    elif "naabu" in t_lower: tool_tokens.extend(["naabu"])
    elif "gitleaks" in t_lower: tool_tokens.extend(["gitleaks"])
    elif "tunneling" in t_lower or "ssh-tunneling" in t_lower: tool_tokens.extend(["ssh", "autossh", "proxychains4", "proxychains"])
    elif "frida" in t_lower: tool_tokens.extend(["frida", "frida-ps", "frida-trace", "objection"])
    elif "jadx" in t_lower: tool_tokens.extend(["jadx", "jadx-gui", "apktool", "apksigner", "zipalign"])
    elif "msfvenom" in t_lower: tool_tokens.extend(["msfvenom", "msfconsole"])
    elif "sliver" in t_lower: tool_tokens.extend(["sliver", "sliver-server", "sliver-client", "generate", "mtls", "https"])
    elif "pacu" in t_lower: tool_tokens.extend(["pacu", "python3 pacu.py", "run"])
    elif "prowler" in t_lower: tool_tokens.extend(["prowler"])
    elif "radare2" in t_lower or t_lower == "r2": tool_tokens.extend(["r2", "radare2", "rabin2", "rasm2", "radiff2", "rafind2", "rax2"])
    elif "strace" in t_lower or "ltrace" in t_lower: tool_tokens.extend(["strace", "ltrace"])
    elif "pwndbg" in t_lower or "gef" in t_lower: tool_tokens.extend(["gdb", "pwndbg", "gef", "telescope", "vmmap", "checksec", "canary", "cyclic", "rop"])
    elif "ropper" in t_lower or "ropgadget" in t_lower: tool_tokens.extend(["ropper", "ropgadget"])
    elif "checksec" in t_lower or "binutils" in t_lower: tool_tokens.extend(["checksec", "readelf", "objdump", "nm", "strings"])
    elif "jwt_tool" in t_lower: tool_tokens.extend(["jwt_tool", "python3 jwt_tool.py", "python jwt_tool.py"])
    elif "gittools" in t_lower: tool_tokens.extend(["extractor.sh", "dumper.sh", "finder.sh", "./extractor.sh", "./dumper.sh", "./finder.sh", "bash"])
    elif "smuggler" in t_lower: tool_tokens.extend(["smuggler", "python3 smuggler.py", "python smuggler.py"])
    elif "corsy" in t_lower: tool_tokens.extend(["corsy", "python3 corsy.py", "python corsy.py"])
    elif "inql" in t_lower: tool_tokens.extend(["inql"])
    elif "mitmproxy" in t_lower: tool_tokens.extend(["mitmproxy", "mitmdump", "mitmweb"])
    elif "scapy" in t_lower: tool_tokens.extend(["scapy"])
    elif "ettercap" in t_lower: tool_tokens.extend(["ettercap", "etterfilter", "etterlog"])
    elif "yersinia" in t_lower: tool_tokens.extend(["yersinia"])
    elif "name-that-hash" in t_lower or "nth" in t_lower: tool_tokens.extend(["nth", "name-that-hash"])
    elif "lazagne" in t_lower: tool_tokens.extend(["lazagne", "lazagne.exe", "python3 lazagne.py", "lazagne.py"])
    elif "cupp" in t_lower: tool_tokens.extend(["cupp", "cupp.py", "python3 cupp.py"])
    elif "fcrackzip" in t_lower: tool_tokens.extend(["fcrackzip"])
    elif "theharvester" in t_lower: tool_tokens.extend(["theharvester", "theHarvester"])
    elif "sherlock" in t_lower: tool_tokens.extend(["sherlock"])
    elif "dnsrecon" in t_lower: tool_tokens.extend(["dnsrecon"])
    elif "hcxdumptool" in t_lower: tool_tokens.extend(["hcxdumptool", "hcxpcapngtool"])
    elif "reaver" in t_lower: tool_tokens.extend(["reaver", "wash", "bully", "pixiewps"])
    elif "trivy" in t_lower: tool_tokens.extend(["trivy"])
    elif "kube-bench" in t_lower: tool_tokens.extend(["kube-bench"])
    elif "searchsploit" in t_lower: tool_tokens.extend(["searchsploit", "gcc"])
    elif "enum4linux-ng" in t_lower or "enum4linux" in t_lower: tool_tokens.extend(["enum4linux-ng", "enum4linux", "enum4linux-ng.py", "python3 enum4linux-ng.py"])
    elif "pwntools" in t_lower or t_lower == "pwn": tool_tokens.extend(["pwn", "python3", "python"])
    elif "pypykatz" in t_lower: tool_tokens.extend(["pypykatz"])
    elif "wafw00f" in t_lower: tool_tokens.extend(["wafw00f"])

    primary = []
    secondary = []
    pkg_prefixes = ("sudo apt", "apt install", "brew install", "choco install", "yum install", "pip install", "pipx install", "cargo install", "go install", "tar ", "gzip ", "unzip ", "git clone", "cd ")

    for block in blocks:
        # Skip ascii art / diagram blocks
        if any(c in "█═╔╗║╚╝▀▄▌▐░▒▓" for c in block) or re.search(r"[/\\_]{5,}", block) or "+---+" in block:
            continue
        for line in block.splitlines():
            cmd = clean_command(line)
            if not cmd or not is_valid_quick_command(cmd):
                continue
            is_install = any(cmd.startswith(p) for p in pkg_prefixes)
            first_w = cmd.split()[0].lower()
            if any(t in first_w for t in tool_tokens):
                if cmd not in primary:
                    primary.append(cmd)
            elif not is_install:
                if cmd not in secondary:
                    secondary.append(cmd)

    return (primary + secondary)[:25]

def build_database(repo_root, output_db_path):
    print(f"[*] Starting Hacking-Cheatsheets database compilation...")
    os.makedirs(os.path.dirname(output_db_path), exist_ok=True)
    
    if os.path.exists(output_db_path):
        os.remove(output_db_path)

    conn = sqlite3.connect(output_db_path)
    cur = conn.cursor()

    # Create tables exactly matching Room schema
    cur.execute("""
    CREATE TABLE IF NOT EXISTS cheatsheets (
        id TEXT NOT NULL PRIMARY KEY,
        category TEXT NOT NULL,
        title TEXT NOT NULL,
        title_tr TEXT NOT NULL,
        description TEXT,
        description_tr TEXT,
        icon TEXT,
        content_en TEXT NOT NULL,
        content_tr TEXT NOT NULL,
        content_it TEXT,
        quick_commands_json TEXT,
        tags TEXT
    );
    """)

    cur.execute("CREATE INDEX IF NOT EXISTS index_cheatsheets_category ON cheatsheets (category);")
    cur.execute("CREATE INDEX IF NOT EXISTS index_cheatsheets_title ON cheatsheets (title);")
    cur.execute("CREATE INDEX IF NOT EXISTS index_cheatsheets_title_tr ON cheatsheets (title_tr);")

    cur.execute("""
    CREATE TABLE IF NOT EXISTS favorites (
        cheatsheet_id TEXT NOT NULL PRIMARY KEY,
        bookmarked_at INTEGER NOT NULL
    );
    """)

    cur.execute("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY, identity_hash TEXT);")
    cur.execute("INSERT OR REPLACE INTO room_master_table (id, identity_hash) VALUES(42, '70698166ea2cb273e0787e8f276526c1');")

    # Find and process all cheatsheets
    count = 0
    for root, dirs, files in os.walk(repo_root):
        if any(x in root for x in [".git", "android", "scripts"]):
            continue
        for f in files:
            if f.endswith(".md") and not f.endswith(".tr.md") and not f.endswith(".it.md"):
                en_path = os.path.join(root, f)
                rel_path = os.path.relpath(en_path, repo_root)
                parts = rel_path.split(os.sep)

                # Skip root README/CONTRIBUTING if desired, or keep as General Reference
                if len(parts) == 1:
                    slug = f[:-3].lower()
                    folder = "Reporting & Methodologies"
                else:
                    folder = parts[0]
                    base_name = f[:-3]
                    if base_name == "README":
                        slug = folder.lower()
                    else:
                        slug = f"{folder.lower()}-{base_name.lower()}"

                category = CATEGORY_MAPPING.get(folder, folder.replace("-", " "))

                # Find Turkish file
                base_no_ext = f[:-3]
                tr_cand1 = os.path.join(root, "translations", f"{base_no_ext}.tr.md")
                tr_cand2 = os.path.join(root, f"{base_no_ext}.tr.md")
                tr_path = tr_cand1 if os.path.exists(tr_cand1) else (tr_cand2 if os.path.exists(tr_cand2) else None)

                # Find Italian file
                it_cand1 = os.path.join(root, "translations", f"{base_no_ext}.it.md")
                it_cand2 = os.path.join(root, f"{base_no_ext}.it.md")
                it_path = it_cand1 if os.path.exists(it_cand1) else (it_cand2 if os.path.exists(it_cand2) else None)

                with open(en_path, "r", encoding="utf-8", errors="ignore") as fp:
                    content_en = fp.read()

                content_tr = ""
                if tr_path and os.path.exists(tr_path):
                    with open(tr_path, "r", encoding="utf-8", errors="ignore") as fp:
                        content_tr = fp.read()
                else:
                    content_tr = content_en

                content_it = None
                if it_path and os.path.exists(it_path):
                    with open(it_path, "r", encoding="utf-8", errors="ignore") as fp:
                        content_it = fp.read()

                title_en = extract_title(content_en, slug.title())
                title_tr = extract_title(content_tr, title_en)
                desc_en = extract_description(content_en)
                desc_tr = extract_description(content_tr)
                quick_cmds = extract_quick_commands(content_en, tool_name=folder)
                quick_cmds_json = json.dumps(quick_cmds)

                # Auto-generate search tags
                tags_list = [slug, folder.lower(), category.lower()]
                if "nmap" in slug: tags_list.extend(["port", "network", "recon", "scan"])
                if "pivoting" in category.lower(): tags_list.extend(["tunnel", "proxy", "socks", "vpn"])
                if "active directory" in category.lower(): tags_list.extend(["kerberos", "domain", "dc", "ldap", "smb"])
                if "privilege" in category.lower(): tags_list.extend(["root", "system", "privesc", "escalation"])
                if "web" in category.lower() or "payload" in category.lower(): tags_list.extend(["owasp", "injection", "http", "api"])
                if "tshark" in slug or "wireshark" in slug: tags_list.extend(["pcap", "packet", "sniff", "traffic", "network"])
                if "dirsearch" in slug: tags_list.extend(["fuzz", "directory", "brute", "path", "web"])
                if "rlwrap" in slug: tags_list.extend(["shell", "tty", "reverse", "readline", "netcat", "nc"])
                if "netcat" in slug or "nc" in slug: tags_list.extend(["nc", "ncat", "socket", "reverse", "shell", "listener", "port", "transfer", "openbsd", "traditional"])
                if "socat" in slug: tags_list.extend(["socket", "pty", "ssl", "relay", "port", "forward"])
                if "proxychains" in slug: tags_list.extend(["socks", "proxy", "pivot", "tunnel", "traffic", "preload"])
                if "sshuttle" in slug: tags_list.extend(["vpn", "ssh", "transparent", "subnet", "pivot", "iptables"])
                if "rustscan" in slug: tags_list.extend(["port", "scanner", "rust", "fast", "nmap"])
                if "masscan" in slug: tags_list.extend(["port", "scanner", "syn", "asynchronous", "internet"])
                if "wpscan" in slug: tags_list.extend(["wordpress", "plugin", "theme", "cms", "vuln"])
                if "kiterunner" in slug: tags_list.extend(["api", "rest", "graphql", "swagger", "openapi", "route"])
                if "wfuzz" in slug: tags_list.extend(["fuzz", "parameter", "web", "matrix", "hidden"])
                if "dnsx" in slug: tags_list.extend(["dns", "resolve", "subdomain", "wildcard", "ptr"])
                if "snaffler" in slug: tags_list.extend(["smb", "share", "keepass", "kdbx", "secret", "cred"])
                if "coercer" in slug: tags_list.extend(["rpc", "coerce", "petitpotam", "printerbug", "relay", "ntlm"])
                if "pkinit" in slug: tags_list.extend(["pkinit", "kerberos", "tgt", "unpac", "shadow", "adcs"])
                if "cewl" in slug: tags_list.extend(["wordlist", "spider", "crawl", "password", "custom"])
                if "crunch" in slug: tags_list.extend(["wordlist", "pattern", "mask", "pin", "generator"])
                if "trufflehog" in slug: tags_list.extend(["secret", "git", "api", "key", "token", "credential", "verify"])
                if "commix" in slug: tags_list.extend(["command", "injection", "rce", "os", "exploit", "web"])
                if "tplmap" in slug: tags_list.extend(["ssti", "template", "jinja2", "twig", "freemarker", "exploit"])
                if "whatweb" in slug: tags_list.extend(["fingerprint", "cms", "technology", "recon", "server", "web"])
                if "adidnsdump" in slug: tags_list.extend(["adidns", "dns", "ldap", "active directory", "zone", "recon"])
                if "naabu" in slug: tags_list.extend(["port", "scanner", "syn", "fast", "projectdiscovery", "connect"])
                if "gitleaks" in slug: tags_list.extend(["secret", "git", "api", "key", "token", "commit", "history"])
                if "tunneling" in slug: tags_list.extend(["ssh", "tunnel", "pivot", "forward", "socks", "proxychains", "dynamic"])
                if "frida" in slug: tags_list.extend(["mobile", "android", "ios", "hooking", "sslpinning", "objection", "root"])
                if "jadx" in slug: tags_list.extend(["mobile", "android", "apk", "dex", "decompile", "apktool", "smali", "repackage"])
                if "msfvenom" in slug: tags_list.extend(["payload", "metasploit", "shellcode", "reverse", "stageless", "staged", "encoder"])
                if "sliver" in slug: tags_list.extend(["c2", "command", "control", "implant", "beacon", "redteam", "post-exploit"])
                if "pacu" in slug: tags_list.extend(["aws", "cloud", "privesc", "iam", "s3", "rhino", "exploit"])
                if "prowler" in slug: tags_list.extend(["cloud", "aws", "azure", "gcp", "cis", "compliance", "audit"])
                if "radare2" in slug or "r2" in slug: tags_list.extend(["reverse", "disassembly", "hex", "patch", "binary", "rabin2", "rasm2"])
                if "strace" in slug or "ltrace" in slug: tags_list.extend(["syscall", "library", "dynamic", "trace", "process", "libc"])
                if "pwndbg" in slug or "gef" in slug: tags_list.extend(["gdb", "pwn", "exploit", "heap", "stack", "context", "canary"])
                if "ropper" in slug or "rop" in slug: tags_list.extend(["rop", "jop", "gadget", "chain", "nx", "dep", "bypass"])
                if "checksec" in slug: tags_list.extend(["mitigation", "relro", "canary", "pie", "nx", "elf", "readelf", "objdump"])
                if "jwt_tool" in slug or "jwt" in slug: tags_list.extend(["jwt", "token", "jwks", "none", "alg", "crypto", "tamper"])
                if "gittools" in slug: tags_list.extend(["git", "commit", "dumper", "extractor", "finder", "repo", "dotgit"])
                if "smuggler" in slug: tags_list.extend(["smuggling", "cl.te", "te.cl", "te.te", "desync", "http", "request"])
                if "corsy" in slug: tags_list.extend(["cors", "origin", "headers", "misconfiguration", "api", "wildcard"])
                if "inql" in slug: tags_list.extend(["graphql", "introspection", "query", "mutation", "schema", "api"])
                if "mitmproxy" in slug: tags_list.extend(["mitm", "proxy", "interception", "flow", "ssl", "dump", "web"])
                if "scapy" in slug: tags_list.extend(["packet", "forge", "craft", "sniff", "layer", "python", "pcap"])
                if "ettercap" in slug: tags_list.extend(["mitm", "arp", "dns", "filter", "poisoning", "sniff", "lan"])
                if "yersinia" in slug: tags_list.extend(["layer2", "stp", "cdp", "dtp", "dhcp", "hsrp", "vlan", "switch"])
                if "name-that-hash" in slug or "nth" in slug: tags_list.extend(["hash", "identify", "hashcat", "john", "algorithm", "nth"])
                if "lazagne" in slug: tags_list.extend(["post-exploitation", "credentials", "password", "browser", "wifi", "dpapi"])
                if "cupp" in slug: tags_list.extend(["wordlist", "profiler", "password", "dictionary", "osint", "social", "human"])
                if "fcrackzip" in slug: tags_list.extend(["zip", "archive", "crack", "password", "pkzip", "bruteforce"])
                if "theharvester" in slug: tags_list.extend(["osint", "recon", "subdomain", "email", "shodan", "whois", "intel"])
                if "sherlock" in slug: tags_list.extend(["osint", "username", "social", "media", "handle", "recon", "account"])
                if "dnsrecon" in slug: tags_list.extend(["dns", "axfr", "zone", "transfer", "ptr", "srv", "snoop", "enum"])
                if "hcxdumptool" in slug: tags_list.extend(["wifi", "pmkid", "wpa2", "wpa3", "hashcat", "eapol", "clientless"])
                if "reaver" in slug: tags_list.extend(["wifi", "wps", "pixiedust", "pin", "wpa2", "bully", "wash"])
                if "trivy" in slug: tags_list.extend(["container", "docker", "k8s", "cve", "vuln", "secret", "sbom", "iac"])
                if "kube-bench" in slug: tags_list.extend(["kubernetes", "k8s", "cis", "benchmark", "controlplane", "audit", "node"])
                if "searchsploit" in slug: tags_list.extend(["cve", "exploit", "exploitdb", "edb", "poc", "offline", "search"])
                if "enum4linux" in slug: tags_list.extend(["smb", "samba", "rpc", "rid", "users", "shares", "netbios", "samr"])
                if "pwntools" in slug or "pwn" in slug: tags_list.extend(["pwn", "ctf", "bufferoverflow", "rop", "cyclic", "shellcraft", "binary", "elf"])
                if "pypykatz" in slug: tags_list.extend(["lsass", "minidump", "mimikatz", "sam", "system", "ntlm", "kerberos", "dpapi"])
                if "wafw00f" in slug: tags_list.extend(["waf", "firewall", "cloudflare", "fingerprint", "modsecurity", "bypass", "web"])
                tags = ",".join(set(tags_list))

                icon = "security"
                if "Web" in category or "Payload" in category: icon = "language"
                elif "Active Directory" in category: icon = "domain"
                elif "Pivoting" in category: icon = "alt_route"
                elif "Privilege" in category: icon = "arrow_upward"
                elif "Recon" in category: icon = "search"
                elif "Forensics" in category: icon = "biotech"
                elif "Reverse" in category: icon = "code"
                elif "Cloud" in category: icon = "cloud"
                elif "Blue" in category: icon = "shield"

                cur.execute("""
                INSERT INTO cheatsheets (
                    id, category, title, title_tr, description, description_tr,
                    icon, content_en, content_tr, content_it, quick_commands_json, tags
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """, (
                    slug, category, title_en, title_tr, desc_en, desc_tr,
                    icon, content_en, content_tr, content_it, quick_cmds_json, tags
                ))

                count += 1

    conn.commit()
    conn.close()

    db_size_mb = os.path.getsize(output_db_path) / (1024 * 1024)
    print(f"[+] Successfully compiled {count} cheatsheets into '{output_db_path}' ({db_size_mb:.2f} MB)")
    return count

if __name__ == "__main__":
    repo_root = os.path.abspath(os.path.join(os.path.dirname(__file__), ".."))
    out_db = os.path.join(repo_root, "android", "app", "src", "main", "assets", "cheatsheets.db")
    build_database(repo_root, out_db)
