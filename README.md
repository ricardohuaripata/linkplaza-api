<!-- PROJECT LOGO -->
<br />
<h1 align="center">
  <a href="https://github.com/Braineanear/EcommerceAPI">
    <img src="https://github.com/user-attachments/assets/cb8ef255-cdeb-4c78-9aa7-550bf88d8185" alt="Logo" width="100" height="100">
  </a>
  <h3 align="center">LinkPlaza API</h3>
</h1>
<h4 align="center">Linktree clone built using Spring Boot & MySQL</h4>

## Key Features

* **Auth Controller**
  * <span style="color:blue;"><strong>POST</strong></span> /api/v1/auth/signup - Registro de usuario.
  * <span style="color:blue;"><strong>POST</strong></span> /api/v1/auth/signin - Inicio de sesión de usuario.
  * <span style="color:blue;"><strong>POST</strong></span> /api/v1/auth/forgot-password - Solicitar restablecimiento de contraseña.
  * <span style="color:blue;"><strong>POST</strong></span> /api/v1/auth/reset-password - Restablecer contraseña.
* **User Controller**
  * <span style="color:green;"><strong>GET</strong></span> /api/v1/user/{id} - Obtener usuario por ID.
  * <span style="color:green;"><strong>GET</strong></span> /api/v1/user/account/info - Obtener información del usuario autenticado.
  * <span style="color:orange;"><strong>PATCH</strong></span> /api/v1/user/account/email - Cambiar correo electrónico del usuario.
  * <span style="color:orange;"><strong>PATCH</strong></span> /api/v1/user/account/password - Cambiar contraseña del usuario.
  * <span style="color:blue;"><strong>POST</strong></span> /api/v1/user/account/signout - Cerrar sesión del usuario.
  * <span style="color:blue;"><strong>POST</strong></span> /api/v1/user/account/verify - Verificar cuenta del usuario.
  * <span style="color:red;"><strong>DELETE</strong></span> /api/v1/user/account - Eliminar cuenta del usuario.
  * <span style="color:blue;"><strong>POST</strong></span> /api/v1/user/account/send-account-verification-code - Enviar código de verificación de cuenta.
  * <span style="color:blue;"><strong>POST</strong></span> /api/v1/user/account/send-delete-account-verification-code - Enviar código de verificación para eliminar cuenta.
* **Page Controller**
  * <span style="color:green;"><strong>GET</strong></span> /api/v1/page/{url} - Obtener página por URL.
  * <span style="color:blue;"><strong>POST</strong></span> /api/v1/page - Crear nueva página.
  * <span style="color:orange;"><strong>PATCH</strong></span> /api/v1/page/{id} - Actualizar página.
  * <span style="color:orange;"><strong>PATCH</strong></span> /api/v1/page/{id}/picture - Subir imagen de la página.
  * <span style="color:red;"><strong>DELETE</strong></span> /api/v1/page/{id} - Eliminar página.
  * <span style="color:blue;"><strong>POST</strong></span> /api/v1/page/{id}/social-link - Agregar enlace social a la página.
  * <span style="color:purple;"><strong>PUT</strong></span> /api/v1/page/{id}/social-link/sort - Ordenar enlaces sociales.
  * <span style="color:orange;"><strong>PATCH</strong></span> /api/v1/page/social-link/{id} - Actualizar enlace social.
  * <span style="color:red;"><strong>DELETE</strong></span> /api/v1/page/social-link/{id} - Eliminar enlace social.
  * <span style="color:blue;"><strong>POST</strong></span> /api/v1/page/{id}/custom-link - Agregar enlace personalizado a la página.
  * <span style="color:purple;"><strong>PUT</strong></span> /api/v1/page/{id}/custom-link/sort - Ordenar enlaces personalizados.
  * <span style="color:orange;"><strong>PATCH</strong></span> /api/v1/page/custom-link/{id} - Actualizar enlace personalizado.
  * <span style="color:red;"><strong>DELETE</strong></span> /api/v1/page/custom-link/{id} - Eliminar enlace personalizado.
* **Analytic Controller**
  * <span style="color:blue;"><strong>POST</strong></span> /api/v1/analytic/visit - Registrar visita a una página.
  * <span style="color:blue;"><strong>POST</strong></span> /api/v1/analytic/social-link-click - Registrar clic en enlace social.
  * <span style="color:blue;"><strong>POST</strong></span> /api/v1/analytic/custom-link-click - Registrar clic en enlace personalizado.
  * <span style="color:green;"><strong>GET</strong></span> /api/v1/analytic - Obtener analíticas de página por rango de fechas.
