<!-- PROJECT LOGO -->
<br />
<h1 align="center">
  <a href="https://github.com/Braineanear/EcommerceAPI">
    <img src="https://github.com/user-attachments/assets/cb8ef255-cdeb-4c78-9aa7-550bf88d8185" alt="Logo" width="100" height="100">
  </a>
  <h3 align="center">LinkPlaza API</h3>
</h1>
<h4>A RESTful API for a link management platform, allowing users to centralize and customize their web and social media links.</h4>

## Endpoints

<style>
  .http-get { color: green; font-weight: bold; }
  .http-post { color: blue; font-weight: bold; }
  .http-patch { color: orange; font-weight: bold; }
  .http-delete { color: red; font-weight: bold; }
  .http-put { color: purple; font-weight: bold; }
</style>

* **Auth Controller**
  * <span class="http-post">POST</span> /api/v1/auth/signup - Registro de usuario.
  * <span class="http-post">POST</span> /api/v1/auth/signin - Inicio de sesión de usuario.
  * <span class="http-post">POST</span> /api/v1/auth/forgot-password - Solicitar restablecimiento de contraseña.
  * <span class="http-post">POST</span> /api/v1/auth/reset-password - Restablecer contraseña.
* **User Controller**
  * <span class="http-get">GET</span> /api/v1/user/{id} - Obtener usuario por ID.
  * <span class="http-get">GET</span> /api/v1/user/account/info - Obtener información del usuario autenticado.
  * <span class="http-patch">PATCH</span> /api/v1/user/account/email - Cambiar correo electrónico del usuario.
  * <span class="http-patch">PATCH</span> /api/v1/user/account/password - Cambiar contraseña del usuario.
  * <span class="http-post">POST</span> /api/v1/user/account/signout - Cerrar sesión del usuario.
  * <span class="http-post">POST</span> /api/v1/user/account/verify - Verificar cuenta del usuario.
  * <span class="http-delete">DELETE</span> /api/v1/user/account - Eliminar cuenta del usuario.
  * <span class="http-post">POST</span> /api/v1/user/account/send-account-verification-code - Enviar código de verificación de cuenta.
  * <span class="http-post">POST</span> /api/v1/user/account/send-delete-account-verification-code - Enviar código de verificación para eliminar cuenta.
* **Page Controller**
  * <span class="http-get">GET</span> /api/v1/page/{url} - Obtener página por URL.
  * <span class="http-post">POST</span> /api/v1/page - Crear nueva página.
  * <span class="http-patch">PATCH</span> /api/v1/page/{id} - Actualizar página.
  * <span class="http-patch">PATCH</span> /api/v1/page/{id}/picture - Subir imagen de la página.
  * <span class="http-delete">DELETE</span> /api/v1/page/{id} - Eliminar página.
  * <span class="http-post">POST</span> /api/v1/page/{id}/social-link - Agregar enlace social a la página.
  * <span class="http-put">PUT</span> /api/v1/page/{id}/social-link/sort - Ordenar enlaces sociales.
  * <span class="http-patch">PATCH</span> /api/v1/page/social-link/{id} - Actualizar enlace social.
  * <span class="http-delete">DELETE</span> /api/v1/page/social-link/{id} - Eliminar enlace social.
  * <span class="http-post">POST</span> /api/v1/page/{id}/custom-link - Agregar enlace personalizado a la página.
  * <span class="http-put">PUT</span> /api/v1/page/{id}/custom-link/sort - Ordenar enlaces personalizados.
  * <span class="http-patch">PATCH</span> /api/v1/page/custom-link/{id} - Actualizar enlace personalizado.
  * <span class="http-delete">DELETE</span> /api/v1/page/custom-link/{id} - Eliminar enlace personalizado.
* **Analytic Controller**
  * <span class="http-post">POST</span> /api/v1/analytic/visit - Registrar visita a una página.
  * <span class="http-post">POST</span> /api/v1/analytic/social-link-click - Registrar clic en enlace social.
  * <span class="http-post">POST</span> /api/v1/analytic/custom-link-click - Registrar clic en enlace personalizado.
  * <span class="http-get">GET</span> /api/v1/analytic - Obtener analíticas de página por rango de fechas.
