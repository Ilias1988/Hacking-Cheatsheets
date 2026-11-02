# 🖼️ OSINT de imagens

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Image-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Image-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

> **Notas de precisão:** EXIF, IPTC e XMP são formatos de metadados; OCR é reconhecimento ótico de caracteres. As classificações «melhor para rostos» pertencem ao original e não representam uma avaliação comparativa validada. O [ExifTool](https://github.com/exiftool/exiftool) lê e escreve metadados; o exemplo `-all=` é uma operação de remoção, não de análise apenas de leitura. A ausência ou presença de metadados deve ser corroborada com outros elementos, conforme indicado no original.

---

## 🔍 Pesquisa inversa de imagens

| Serviço | URL | Mais indicado para |
|---------|-----|----------|
| **Google Images** | images.google.com | Geral |
| **Yandex** | yandex.com/images | **Rostos** (melhor, segundo o original) |
| **TinEye** | tineye.com | Correspondências exatas |
| **Bing Images** | bing.com/images | Alternativa |
| **PimEyes** | pimeyes.com | Reconhecimento facial |

---

## 📷 Extração de metadados (EXIF)

### ExifTool
```bash
# Ver todos os metadados
exiftool image.jpg

# Coordenadas GPS
exiftool -gps* image.jpg

# Informações da câmara
exiftool -make -model -datetime image.jpg

# Remover metadados
exiftool -all= image.jpg
```

### Ferramentas online
- [Pic2Map](https://www.pic2map.com/)
- [FotoForensics](https://fotoforensics.com/)
- [Metadata Remover Viewer](https://metadataremover.ai/metadata-viewer) — Ferramenta de terceiros para inspecionar EXIF/IPTC/XMP no navegador. Testar com ficheiros sem informação sensível.

> Os metadados podem ser alterados ou removidos; confirme-os com outras provas.

---

## 🔎 Análise de imagens

### Análise forense
```
Funcionalidades do FotoForensics.com:
- ELA (Error Level Analysis, análise do nível de erro)
- Extração de metadados
- Deteção de dados ocultos
- Análise da compressão
```

### Geolocalização a partir de imagens
```
1. Extrair os dados GPS dos metadados EXIF
2. Procurar pontos de referência na imagem
3. Comparar com imagens ao nível da rua
4. Verificar o texto em edifícios/sinalização
```

---

## 📋 Lista de verificação de OSINT de imagens

```markdown
□ Pesquisa inversa de imagens (Google, Yandex)
□ Extrair os metadados EXIF
□ Verificar as coordenadas GPS
□ Pesquisa por reconhecimento facial
□ Analisar possíveis manipulações
□ Pesquisar texto na imagem/OCR (reconhecimento ótico de caracteres)
□ Consultar o histórico da imagem (TinEye)
```

---

**Voltar a OSINT:** [🔍 Visão geral de OSINT](README.pt.md)
