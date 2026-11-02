# 📊 Plantilla de resumen ejecutivo

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Executive-Summary-Template.tr.md) • [🇬🇷 Ελληνικά](Executive-Summary-Template.el.md)

```
███████╗██╗  ██╗███████╗ ██████╗██╗   ██╗████████╗██╗██╗   ██╗███████╗
██╔════╝╚██╗██╔╝██╔════╝██╔════╝██║   ██║╚══██╔══╝██║██║   ██║██╔════╝
█████╗   ╚███╔╝ █████╗  ██║     ██║   ██║   ██║   ██║██║   ██║█████╗  
██╔══╝   ██╔██╗ ██╔══╝  ██║     ██║   ██║   ██║   ██║╚██╗ ██╔╝██╔══╝  
███████╗██╔╝ ██╗███████╗╚██████╗╚██████╔╝   ██║   ██║ ╚████╔╝ ███████╗
╚══════╝╚═╝  ╚═╝╚══════╝ ╚═════╝ ╚═════╝    ╚═╝   ╚═╝  ╚═══╝  ╚══════╝
███████╗██╗   ██╗███╗   ███╗███╗   ███╗ █████╗ ██████╗ ██╗   ██╗
██╔════╝██║   ██║████╗ ████║████╗ ████║██╔══██╗██╔══██╗╚██╗ ██╔╝
███████╗██║   ██║██╔████╔██║██╔████╔██║███████║██████╔╝ ╚████╔╝ 
╚════██║██║   ██║██║╚██╔╝██║██║╚██╔╝██║██╔══██║██╔══██╗  ╚██╔╝  
███████║╚██████╔╝██║ ╚═╝ ██║██║ ╚═╝ ██║██║  ██║██║  ██║   ██║   
╚══════╝ ╚═════╝ ╚═╝     ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   
```

> **Finalidad:** Un resumen conciso y sin detalles técnicos para la alta dirección, el consejo de administración y los responsables de gestión.

---

> **Uso de la plantilla:** las cifras de costes, porcentajes, puntuaciones y comparaciones sectoriales son ejemplos ilustrativos del original; deben sustituirse por datos y estimaciones sustentados en la evaluación. No representan estadísticas verificadas ni garantías de reducción del riesgo.

## 📋 Estructura del resumen ejecutivo

````markdown
# RESUMEN EJECUTIVO DE LA EVALUACIÓN DE SEGURIDAD

**Cliente:** [Company Name]
**Tipo de evaluación:** [Penetration Test / Security Audit / Vulnerability Assessment]
**Fecha:** [Assessment Period]
**Clasificación:** CONFIDENCIAL

---

## 1. Finalidad y alcance

Se contrató a [Your Company] para realizar una evaluación de tipo [type of assessment] de [Company Name]: 
[describe scope in business terms]. El objetivo era evaluar el estado de seguridad 
e identificar los posibles riesgos para las operaciones del negocio.

**El alcance incluyó:**
- [Business system 1]
- [Business system 2]
- [Network/Infrastructure]

---

## 2. Valoración general de seguridad

┌─────────────────────────────────────────────────────┐
│                                                     │
│            RIESGO GENERAL: ALTO                     │
│                                                     │
│    ████████████████████░░░░░░░░░░  70/100           │
│                                                     │
│    Se requiere atención inmediata                   │
│                                                     │
└─────────────────────────────────────────────────────┘

| Valoración | Definición |
|--------|------------|
| 🔴 **Crítica** | Se requiere actuar de inmediato. Alta probabilidad de una brecha de seguridad. |
| 🟠 **Alta** | Vulnerabilidades importantes. Se necesita una corrección urgente. |
| 🟡 **Media** | Riesgo moderado. Planificar la corrección en 30-60 días. |
| 🟢 **Baja** | Problemas menores. Resolver durante el mantenimiento habitual. |

---

## 3. Resumen de los principales hallazgos

| Severidad | Cantidad | Ejemplos |
|----------|-------|----------|
| 🔴 Crítica | X | Acceso a bases de datos y evasión de autenticación |
| 🟠 Alta | X | Exposición de datos y escalada de privilegios |
| 🟡 Media | X | Problemas de configuración y acceso limitado a datos |
| 🟢 Baja | X | Recomendaciones de buenas prácticas |
| **Total** | **X** | |

### Distribución de hallazgos

```
Crítica  ████████░░░░░░░░░░░░  X hallazgos (XX%)
Alta      ██████████████░░░░░░  X hallazgos (XX%)
Media    ████████████░░░░░░░░  X hallazgos (XX%)
Baja       ████░░░░░░░░░░░░░░░░  X hallazgos (XX%)
````

---

## 4. Evaluación del impacto en el negocio

### Riesgo financiero
| Categoría de riesgo | Impacto potencial | Probabilidad |
|---------------|------------------|------------|
| Brecha de datos | $X - $X millones | Alta |
| Interrupción del negocio | $X/día de inactividad | Media |
| Multas regulatorias | $X (GDPR/PCI-DSS) | Alta |
| Daño reputacional | Significativo | Alta |

### Visualización del riesgo

```
                    PROBABILIDAD
              Baja     Media      Alta
         ┌────────┬─────────┬────────┐
    Alta │ Media  │  Alta   │Crítica │  IMPACTO
         ├────────┼─────────┼────────┤
   Media │ Baja   │ Media   │  Alta  │
         ├────────┼─────────┼────────┤
    Baja │ Baja   │ Baja    │ Media  │
         └────────┴─────────┴────────┘
```

### Principales riesgos de negocio identificados

1. **Exposición de datos de clientes**
   - X registros de clientes potencialmente accesibles
   - Implicaciones relativas al RGPD y a la normativa de privacidad

2. **Continuidad del negocio**
   - Sistemas esenciales vulnerables a interrupciones
   - Posibilidad de X horas/días de inactividad

3. **Propiedad intelectual**
   - [Specific risk to proprietary data]

---

## 5. Hallazgos críticos (requieren actuación inmediata)

### Hallazgo 1: [Title - Business Impact Focus]
> **Riesgo:** Un atacante podría [business impact in plain language]
>
> **Afectado:** [Business system/process]
>
> **Recomendación:** [High-level fix]

### Hallazgo 2: [Title]
> **Riesgo:** [Business impact]
>
> **Afectado:** [System]
>
> **Recomendación:** [Fix]

---

## 6. Recomendaciones priorizadas

### Inmediatas (0-30 días)
| Prioridad | Acción | Inversión | Reducción del riesgo |
|----------|--------|------------|----------------|
| 1 | [Action 1] | $ | Alta |
| 2 | [Action 2] | $$ | Alta |
| 3 | [Action 3] | $ | Media |

### A corto plazo (30-90 días)
| Prioridad | Acción | Inversión | Reducción del riesgo |
|----------|--------|------------|----------------|
| 4 | [Action 4] | $$ | Media |
| 5 | [Action 5] | $$$ | Media |

### A largo plazo (más de 90 días)
| Prioridad | Acción | Inversión | Reducción del riesgo |
|----------|--------|------------|----------------|
| 6 | [Action 6] | $$$ | Baja |
| 7 | [Action 7] | $$$$ | Estratégica |

---

## 7. Inversión frente a reducción del riesgo

```
    Riesgo                                        Inversión
    Reducción                                   Necesaria
    ▲                                           
    │     ┌─────┐                               
100%│     │ P1  │ ← Resultados rápidos                  $
    │     └─────┘   (gran valor, bajo coste)      
    │                                           
 75%│        ┌─────┐                            
    │        │ P2  │                            $$
    │        └─────┘                            
    │                                           
 50%│              ┌─────┐                      
    │              │ P3  │                      $$$
    │              └─────┘                      
    │                                           
 25%│                    ┌─────┐                
    │                    │ P4  │                $$$$
    │                    └─────┘                
    └────────────────────────────────────────► 
         $      $$      $$$     $$$$
                   Presupuesto
```

---

## 8. Comparación de madurez de seguridad

### Situación actual frente a la referencia del sector

| Área | Actual | Media del sector | Objetivo |
|--------|---------|--------------|--------|
| Control de acceso | ██░░░░░░░░ 20% | ████████░░ 80% | ████████░░ 80% |
| Protección de datos | ████░░░░░░ 40% | ███████░░░ 70% | ████████░░ 85% |
| Supervisión | ███░░░░░░░ 30% | ██████░░░░ 60% | ████████░░ 80% |
| Respuesta a incidentes | █░░░░░░░░░ 10% | █████░░░░░ 50% | ███████░░░ 70% |

---

## 9. Conclusión

El estado de seguridad actual de [Company Name] representa un riesgo **[HIGH/MEDIUM/LOW]** para 
las operaciones del negocio. La evaluación identificó **X** vulnerabilidades, entre ellas 
**X críticas** que requieren atención inmediata.

**Puntos principales:**
1. [Most important point for executives]
2. [Second most important point]
3. [Third most important point]

**Inversión recomendada:** $XXX,XXX - $XXX,XXX durante 12 meses

**Resultado esperado:** Reducción del riesgo de seguridad del X%

---

## 10. Próximos pasos

| Cronología | Acción | Responsable | Estado |
|----------|--------|-------|--------|
| Inmediato | Resolver los hallazgos críticos | Equipo de seguridad | Pendiente |
| Semana 1 | Revisar el informe detallado | Director de TI | Pendiente |
| Semana 2 | Aprobar el presupuesto para la corrección | CFO | Pendiente |
| Mes 1 | Iniciar la corrección | Equipo de seguridad | Pendiente |
| Mes 3 | Volver a comprobar los hallazgos críticos | [Vendor] | Pendiente |

---

**Información de contacto:**
[Your Company Name]
[Lead Consultant Name]
[Email] | [Phone]

---

*Este documento es confidencial y está destinado exclusivamente al personal autorizado.*
```

---

## 🎯 Resumen ejecutivo de una página

````markdown
┌──────────────────────────────────────────────────────────────────────────────┐
│                    RESUMEN DE LA EVALUACIÓN DE SEGURIDAD                     │
│                                [Company Name]                                │
│                                    [Date]                                    │
├──────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  VALORACIÓN GENERAL          RESUMEN DE HALLAZGOS                            │
│  ┌───────────┐               ┌──────────────────────────┐                    │
│  │           │               │ 🔴 Crítica: X             │                    │
│  │    ALTO   │               │ 🟠 Alta: X                │                    │
│  │   RIESGO  │               │ 🟡 Media: X               │                    │
│  │           │               │ 🟢 Baja: X                │                    │
│  └───────────┘               │ Total: X                 │                    │
│                              └──────────────────────────┘                    │
│                                                                              │
│  TRES RIESGOS PRINCIPALES                                                    │
│  ════════════════════════                                                    │
│  1. [Critical risk - one line description]                                   │
│  2. [High risk - one line description]                                       │
│  3. [High risk - one line description]                                       │
│                                                                              │
│  IMPACTO EN EL NEGOCIO                                                       │
│  ═════════════════════                                                       │
│  • Posible brecha de datos que afecta a X clientes                           │
│  • Multas regulatorias de hasta $X                                           │
│  • Riesgo de interrupción del negocio durante X días                         │
│                                                                              │
│  ACCIONES INMEDIATAS NECESARIAS                                              │
│  ══════════════════════════════                                              │
│  □ [Action 1] - Coste estimado: $XX,XXX                                      │
│  □ [Action 2] - Coste estimado: $XX,XXX                                      │
│  □ [Action 3] - Coste estimado: $XX,XXX                                      │
│                                                                              │
│  INVERSIÓN RECOMENDADA: $XXX,XXX                                             │
│  REDUCCIÓN DEL RIESGO PREVISTA: XX%                                          │
│                                                                              │
└──────────────────────────────────────────────────────────────────────────────┘
````

---

## 📈 Elementos visuales para directivos

### Gráfico de evolución del riesgo
```
Nivel de riesgo
    ▲
100 │
    │         ●─────Antes de la evaluación
 80 │         │
    │         │
 60 │         │
    │         ▼
 40 │              ○ ─ ─ Tras la corrección (objetivo)
    │
 20 │
    │
  0 └───────────────────────────────────────────►
        Q1      Q2      Q3      Q4      Tiempo
```

### Coste de la inacción
````markdown
| Cronología | Coste de la brecha | Coste de corrección | Ahorro |
|----------|----------------|------------------|---------|
| Ahora | $0 | $100K | N/A |
| Tras una brecha | $1.5M | $300K | -$1.3M |
| Con seguro | $500K | $150K | -$350K |
````

---

## 💡 Consejos de redacción para directivos

### Buenas prácticas ✅
```
✓ Empieza por el impacto en el negocio, sin entrar en detalles técnicos
✓ Usa un lenguaje sencillo, sin jerga
✓ Proporciona un análisis claro de costes y beneficios
✓ Incluye recomendaciones que puedan ponerse en práctica
✓ Limita la extensión a 2-3 páginas
✓ Usa elementos visuales (gráficos, diagramas y tablas)
✓ Destaca el retorno de la inversión en seguridad
✓ Compara con las referencias del sector
```

### Prácticas que evitar ❌
```
✗ Evita la terminología técnica (CVE, CVSS, etc.)
✗ No enumeres todos los hallazgos: céntrate en los críticos
✗ No incluyas demasiados detalles técnicos
✗ Recuerda incluir costes y plazos
✗ No lo alargues más de lo necesario
✗ No recurras al miedo sin ofrecer soluciones
```

---

## 📋 Lista de comprobación del resumen ejecutivo

````markdown
## Antes de presentar a la dirección

□ El resumen tiene un máximo de 2-3 páginas
□ No se usa jerga técnica
□ El impacto en el negocio está explicado con claridad
□ Se incluyen costes y plazos
□ Se incluyen elementos visuales (gráficos y diagramas)
□ Las recomendaciones pueden ponerse en práctica
□ Se explica el retorno de la inversión en la corrección
□ Se incluye una comparación con el sector
□ Los siguientes pasos están claramente definidos
□ Se proporciona información de contacto
````

---

**Volver a informes:** [📝 Plantillas de informes](../../README.md#-reporting-templates)
