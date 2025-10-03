# UNAB Store

## José Alejandro Téllez Prada

<img src="https://avatars.githubusercontent.com/u/166764435?v=4" width="100" alt="Avatar de José Alejandro Téllez Prada">


<p align="center">
  <img src="https://unab.edu.co/wp-content/uploads/2025/03/Breves-Tienda-UNAB.png" alt="Logo UNAB" width="220" />
</p>

<p align="center">
  <strong>UNAB Store</strong> — Aplicación móvil oficial (prototipo) para la <em>merch</em> de la Universidad Autónoma de Bucaramanga (UNAB).  
  Diseñada en <strong>Android Studio</strong> para ofrecer una experiencia interactiva, visual y segura al usuario.
</p>

---

## 📌 Tabla de contenido
1. [Descripción](#descripción)
2. [Características](#características)
3. [Tecnologías](#tecnologías)
4. [Instalación y ejecución (Android Studio)](#instalación-y-ejecución-android-studio)
5. [Estructura del proyecto](#estructura-del-proyecto)
6. [Diseño y assets](#diseño-y-assets)
7. [Contribuir](#contribuir)
8. [Licencia y contacto](#licencia-y-contacto)

---

## 📝 Descripción

**UNAB Store** es una aplicación móvil creada en **Android Studio** por **José Alejandro Téllez Prada**.  
La app busca ser la tienda interactiva de la UNAB: catálogo de camisetas, buzos, termos, cuadernos y accesorios oficiales. Más que vender, pretende reforzar el sentido de pertenencia universitaria mediante una interfaz atractiva con microinteracciones, transiciones suaves y componentes modernos de Material Design.

> ✨ La idea es contar con un prototipo funcional que permita exhibir, filtrar y comprar productos —con integración a pasarelas de pago y gestión básica de pedidos— pensado para evolucionar a una solución institucional.

---

## 🌟 Características

- 🛍️ Catálogo de productos con filtros por categoría y búsqueda inteligente.  
- 🎨 UI basada en Material Design: animaciones, tarjetas, efectos de elevación y transiciones compartidas.  
- ❤️ Favoritos y carrito persistente (local + sincronizable).  
- 🔒 Autenticación básica (email / OAuth opcional).  
- 📦 Visualización del estado de pedido y notificaciones locales.  
- ⚙️ Soporte para imágenes de alta calidad (placeholder para assets oficiales UNAB).  
- 🔁 Preparada para integrarse con Firebase para auth, Firestore e imágenes.

---

## 🛠️ Tecnologías

- **IDE:** Android Studio  
- **Lenguaje:** Kotlin (recomendado) — también compatible con Java  
- **UI:** XML + Material Components (Jetpack)  
- **Persistencia:** Room / SharedPreferences / Firebase Firestore (opcional)  
- **Imagenes & Storage:** Firebase Storage o Cloud CDN  
- **Control de versiones:** Git / GitHub

---

## 🚀 Instalación y ejecución (Android Studio)

```bash
# Clona el repositorio
git clone https://github.com/tuusuario/unab-store.git
cd unab-store
