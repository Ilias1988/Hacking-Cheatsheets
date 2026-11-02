# 🖼️ 图像 OSINT

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Image-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Image-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

> **译注：** 原文对某些搜索服务使用“最佳”等评价，属于原作者经验判断；检索结果会受图片、索引和服务能力影响。EXIF 中的日期与位置字段属于待核实线索，不应作为唯一证据。

---

## 🔍 以图搜图

| 服务 | URL | 适用场景 |
|---------|-----|----------|
| **Google Images** | images.google.com | 通用 |
| **Yandex** | yandex.com/images | **人脸**（原文称效果最佳） |
| **TinEye** | tineye.com | 精确匹配 |
| **Bing Images** | bing.com/images | 备选服务 |
| **PimEyes** | pimeyes.com | 人脸识别 |

---

## 📷 提取元数据（EXIF）

### ExifTool
```bash
# 查看全部元数据
exiftool image.jpg

# GPS 坐标
exiftool -gps* image.jpg

# 相机信息
exiftool -make -model -datetime image.jpg

# 移除元数据
exiftool -all= image.jpg
```

### 在线工具
- [Pic2Map](https://www.pic2map.com/)
- [FotoForensics](https://fotoforensics.com/)
- [Metadata Remover Viewer](https://metadataremover.ai/metadata-viewer) — 第三方浏览器端 EXIF/IPTC/XMP 检查工具。请使用不含敏感信息的文件测试。

> 元数据可能被编辑或删除，应结合其他证据交叉验证。

---

## 🔎 图像分析

### 取证分析
```
FotoForensics.com 功能：
- ELA（误差级别分析）
- 提取元数据
- 隐藏数据检测
- 压缩分析
```

### 通过图像定位
```
1. 提取 EXIF 中的 GPS 数据
2. 搜索图像中的地标
3. 使用街景进行比对
4. 检查建筑物与标识上的文字
```

---

## 📋 图像 OSINT 检查清单

```markdown
□ 以图搜图（Google、Yandex）
□ 提取 EXIF 元数据
□ 检查 GPS 坐标
□ 人脸识别搜索
□ 分析是否存在图像修改
□ 搜索图中文字／进行光学字符识别（OCR）
□ 查询图像历史（TinEye）
```

---

**返回 OSINT:** [🔍 OSINT 概览](./README.zh-CN.md)
