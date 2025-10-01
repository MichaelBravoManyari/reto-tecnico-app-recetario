# 📱 Reto Técnico: App de Recetario

## 🎯 Objetivo
Crear una aplicación de Android que permita al usuario explorar un recetario de cocina, ver detalles de recetas y marcarlas como favoritas.

---

## 🚀 Funcionalidades

### 1. Splash Screen
- Aparece al abrir la aplicación.  
- Desaparece automáticamente después de **2 segundos**.  

### 2. Onboarding
- Consta de **3 pantallas consecutivas** para presentar información ficticia sobre la app (ejemplo: cómo buscar recetas, marcar favoritas, etc.).  
- Solo se muestra la **primera vez** que se abre la aplicación.  

### 3. Pantalla Principal
- Presenta un **listado de recetas** obtenidas desde una API creada por el desarrollador.  
- Cada receta incluye:  
  - Nombre  
  - Imagen  
  - Descripción breve  
- Permite al usuario **marcar recetas como favoritas**.  
- Incluye una **sección separada** para mostrar las recetas favoritas.  
- La lista se **actualiza dinámicamente** al marcar o desmarcar recetas como favoritas.  

### 4. Pantalla de Detalle de Receta
- Muestra información detallada de una receta seleccionada:  
  - Nombre  
  - Imagen  
  - Ingredientes  
  - Pasos para preparar la receta  

### 5. Modo Oscuro (Dark Mode)
- La aplicación se adapta automáticamente al **modo oscuro configurado en el sistema**.  

---

## 🛠️ Requisitos Técnicos
- La API puede ser creada con cualquier herramienta (ejemplo: **Postman, Mockoon** o un servidor temporal).  
- Uso de **MVVM** (o arquitectura propuesta por el candidato).  
- Manejo de dependencias con **Dagger Hilt**.  
- Persistencia local para las recetas marcadas como favoritas.  

---

## 🧪 Pruebas
- Incluir **pruebas unitarias** para al menos un caso de uso.  

---

## 📋 Evaluación
1. **Calidad del código**: Limpieza, modularidad y legibilidad.  
2. **Buenas prácticas**: Uso de patrones y librerías modernas.  
3. **Pruebas**: Cobertura adecuada de la lógica principal y flujos clave.  
4. **Experiencia de usuario (UX)**: No se evalúa un diseño perfecto, pero la app debe ser **intuitiva y entendible** para el usuario.  

---
