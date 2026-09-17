# 🤝 Contributing to Hacking Cheatsheets

First off, thank you for considering contributing to Hacking Cheatsheets! 🎉

This document provides guidelines for contributing to this repository.

---

## 📋 Table of Contents

- [Code of Conduct](#-code-of-conduct)
- [How Can I Contribute?](#-how-can-i-contribute)
- [Cheatsheet Guidelines](#-cheatsheet-guidelines)
- [Style Guide](#-style-guide)
- [Pull Request Process](#-pull-request-process)
- [Recognition](#-recognition)

---

## 📜 Code of Conduct

### Our Pledge

We are committed to providing a friendly, safe, and welcoming environment for all contributors.

### Standards

- ✅ Be respectful and inclusive
- ✅ Provide constructive feedback
- ✅ Focus on what is best for the community
- ❌ No harassment or discrimination
- ❌ No illegal content or instructions for malicious use

### Ethical Guidelines

> ⚠️ **All contributions must be for educational and ethical purposes only.**
> 
> We do not accept:
> - Content promoting illegal activities
> - Instructions for unauthorized access
> - Malware or exploit code intended for malicious use

---

## 🎯 How Can I Contribute?

### 📝 Adding New Cheatsheets

Have expertise in a security tool not yet covered? Create a new cheatsheet!

1. Check if the tool is listed in [Planned Cheatsheets](README.md#-cheatsheets)
2. Create a new folder for the tool
3. Follow the [Cheatsheet Guidelines](#-cheatsheet-guidelines)
4. Submit a pull request

### 🔧 Improving Existing Cheatsheets

- Fix typos or grammatical errors
- Add missing commands or options
- Improve explanations with better examples
- Update outdated information
- Add quick reference tables

### 🐛 Reporting Issues

Found an error or have a suggestion?

1. Check if the issue already exists
2. Create a new issue with:
   - Clear description
   - Steps to reproduce (if applicable)
   - Suggested fix (if you have one)

### 🌐 Translations

Help make these cheatsheets accessible to non-English speakers:

1. Create a folder like `Metasploit/translations/`
2. Add translated files with language suffix: `README.es.md`, `README.de.md`
3. Follow the same structure as the original

---

## 📖 Cheatsheet Guidelines

### Structure

Every cheatsheet should follow this structure:

```markdown
# 🔴 Tool Name - Complete Cheatsheet

[ASCII Art Banner - Optional]

[Badges]

> Brief description of the tool

---

## 📋 Table of Contents
- [Section 1](#section-1)
- [Section 2](#section-2)
...

---

## 🎯 Introduction
What is this tool? Why is it used?

---

## 📥 Installation
How to install the tool

---

## ⌨️ Basic Commands
Essential commands with examples

---

## 🎬 Real-World Examples
Practical use cases

---

## 📊 Quick Reference Table
Command summary table

---

## 💡 Tips & Best Practices

---

## ⚠️ Legal Disclaimer

---

## 📚 Resources
Links to official docs and learning resources
```

### Required Sections

| Section | Required | Description |
|---------|----------|-------------|
| Title with emoji | ✅ | Clear tool name |
| Badges | ✅ | At least 2-3 relevant badges |
| Table of Contents | ✅ | Links to all sections |
| Introduction | ✅ | What and why |
| Basic Commands | ✅ | Core functionality |
| Quick Reference | ✅ | Summary table |
| Legal Disclaimer | ✅ | Ethical use warning |
| Installation | 📋 | Recommended |
| Examples | 📋 | Highly recommended |
| Tips | 📋 | Recommended |

---

## 🎨 Style Guide

### Markdown Formatting

```markdown
# H1 - Main Title (only one per file)
## H2 - Major Sections
### H3 - Subsections
#### H4 - Minor sections

**Bold** for emphasis
`code` for commands and file names
```code blocks``` for multi-line commands
> Blockquotes for notes and warnings
```

### Code Blocks

Always specify the language for syntax highlighting:

```bash
# Good - with language
```bash
msfconsole -q
```

# Bad - no language
```
msfconsole -q
```
```

### Tables

Use tables for command references:

```markdown
| Command | Description | Example |
|---------|-------------|---------|
| `cmd1` | Does X | `cmd1 -option` |
| `cmd2` | Does Y | `cmd2 value` |
```

### Emojis

Use emojis consistently:

| Emoji | Usage |
|-------|-------|
| 🔴 | Exploitation/Attack |
| 🔍 | Reconnaissance |
| 🌐 | Web/Network |
| 🔓 | Credentials/Access |
| ⚠️ | Warning |
| 💡 | Tip |
| ✅ | Do/Good |
| ❌ | Don't/Bad |
| 📋 | Reference |
| 🎯 | Target/Goal |

### Badges

Use shields.io badges:

```markdown
![Badge Name](https://img.shields.io/badge/Label-Message-color?style=for-the-badge)
```

Common colors: `red`, `blue`, `green`, `orange`, `purple`

---

## 🔄 Pull Request Process

### Before Submitting

1. ✅ Read the contributing guidelines (this document)
2. ✅ Search for existing PRs addressing the same issue
3. ✅ Test all commands and examples
4. ✅ Check spelling and grammar
5. ✅ Ensure proper formatting

### Submitting a PR

1. **Fork** the repository
2. **Create** a feature branch:
   ```bash
   git checkout -b feature/add-nmap-cheatsheet
   ```
3. **Commit** your changes:
   ```bash
   git commit -m "Add: Nmap cheatsheet with scanning examples"
   ```
4. **Push** to your fork:
   ```bash
   git push origin feature/add-nmap-cheatsheet
   ```
5. **Open** a Pull Request

### Commit Message Format

```
Type: Short description

- Detailed change 1
- Detailed change 2

Closes #123 (if applicable)
```

Types:
- `Add:` New content
- `Fix:` Bug fixes
- `Update:` Updates to existing content
- `Docs:` Documentation changes
- `Style:` Formatting changes

### Review Process

1. Maintainer reviews the PR
2. Feedback provided if changes needed
3. Once approved, PR is merged
4. Your contribution is credited!

---

## 🏆 Recognition

All contributors will be recognized:

- Added to Contributors list
- Mentioned in release notes
- GitHub contribution graph

### Hall of Fame

*Contributors who make significant contributions will be featured here!*

---

## ❓ Questions?

If you have questions:

1. Check existing issues
2. Create a new issue with the `question` label
3. Be patient for a response

---

<p align="center">
  <b>Thank you for contributing! 🙏</b><br>
  <i>Together we make cybersecurity knowledge more accessible</i>
</p>
