<!-- PROJECT LOGO -->
<br />
<h1 align="center">
  <a href="https://github.com/ricardohuaripata/linkplaza-api">
    <img src="https://github.com/user-attachments/assets/cb8ef255-cdeb-4c78-9aa7-550bf88d8185" alt="linkplaza logo" width="100" height="100">
  </a>
  <h3 align="center">LinkPlaza API</h3>
</h1>
<h4>A RESTful API for a link management platform, allowing users to centralize and customize their web and social media links.</h4>

<a href="https://github.com/ricardohuaripata/linkplaza">
    <img src="https://github.com/user-attachments/assets/0c8c0d35-8b0b-4fb6-8f24-11e3c157a3ec" alt="linkplaza thumbnail" width="100%" height="auto">
</a>

## Endpoints

* **Auth Controller**
  * <strong>POST</strong> /api/v1/auth/signup - Registro de usuario.
  * <strong>POST</strong> /api/v1/auth/signin - Inicio de sesión de usuario.
  * <strong>POST</strong> /api/v1/auth/forgot-password - Solicitar restablecimiento de contraseña.
  * <strong>POST</strong> /api/v1/auth/reset-password - Restablecer contraseña.
* **User Controller**
  * <strong>GET</strong> /api/v1/user/{id} - Obtener usuario por ID.
  * <strong>GET</strong> /api/v1/user/account/info - Obtener información del usuario autenticado.
  * <strong>PATCH</strong> /api/v1/user/account/email - Cambiar correo electrónico del usuario.
  * <strong>PATCH</strong> /api/v1/user/account/password - Cambiar contraseña del usuario.
  * <strong>POST</strong> /api/v1/user/account/signout - Cerrar sesión del usuario.
  * <strong>POST</strong> /api/v1/user/account/verify - Verificar cuenta del usuario.
  * <strong>DELETE</strong> /api/v1/user/account - Eliminar cuenta del usuario.
  * <strong>POST</strong> /api/v1/user/account/send-account-verification-code - Enviar código de verificación de cuenta.
  * <strong>POST</strong> /api/v1/user/account/send-delete-account-verification-code - Enviar código de verificación para eliminar cuenta.
* **Page Controller**
  * <strong>GET</strong> /api/v1/page/{url} - Obtener página por URL.
  * <strong>POST</strong> /api/v1/page - Crear nueva página.
  * <strong>PATCH</strong> /api/v1/page/{id} - Actualizar página.
  * <strong>PATCH</strong> /api/v1/page/{id}/picture - Subir imagen de la página.
  * <strong>DELETE</strong> /api/v1/page/{id} - Eliminar página.
  * <strong>POST</strong> /api/v1/page/{id}/social-link - Agregar enlace social a la página.
  * <strong>PUT</strong> /api/v1/page/{id}/social-link/sort - Ordenar enlaces sociales.
  * <strong>PATCH</strong> /api/v1/page/social-link/{id} - Actualizar enlace social.
  * <strong>DELETE</strong> /api/v1/page/social-link/{id} - Eliminar enlace social.
  * <strong>POST</strong> /api/v1/page/{id}/custom-link - Agregar enlace personalizado a la página.
  * <strong>PUT</strong> /api/v1/page/{id}/custom-link/sort - Ordenar enlaces personalizados.
  * <strong>PATCH</strong> /api/v1/page/custom-link/{id} - Actualizar enlace personalizado.
  * <strong>DELETE</strong> /api/v1/page/custom-link/{id} - Eliminar enlace personalizado.
* **Analytic Controller**
  * <strong>POST</strong> /api/v1/analytic/visit - Registrar visita a una página.
  * <strong>POST</strong> /api/v1/analytic/social-link-click - Registrar clic en enlace social.
  * <strong>POST</strong> /api/v1/analytic/custom-link-click - Registrar clic en enlace personalizado.
  * <strong>GET</strong> /api/v1/analytic - Obtener analíticas de página por rango de fechas.
