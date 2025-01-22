<!-- PROJECT LOGO -->
<br />
<h1 align="center">
  <a href="https://github.com/ricardohuaripata/linkplaza-api">
    <img src="https://github.com/user-attachments/assets/cb8ef255-cdeb-4c78-9aa7-550bf88d8185" alt="linkplaza logo" width="100" height="100">
  </a>
  <h1 align="center">LinkPlaza API</h1>
</h1>
<p>A RESTful API for a link management platform, allowing users to centralize and customize their web and social media links. Built using Spring Boot, Spring Data JPA with MySQL and Spring Security with JWT Authentication.</p>

<a href="https://documenter.getpostman.com/view/25547682/2sAYJ3EgtX">Frontend source code</a>
<br>
<a href="https://documenter.getpostman.com/view/25547682/2sAYJ3EgtX">Postman API documentation</a>

<a href="https://github.com/ricardohuaripata/linkplaza">
    <img src="https://github.com/user-attachments/assets/0c8c0d35-8b0b-4fb6-8f24-11e3c157a3ec" alt="linkplaza thumbnail" width="100%" height="auto">
</a>

## Endpoints

* **Auth Controller**
  * <strong>POST</strong> /api/v1/auth/signup - User sign up.
  * <strong>POST</strong> /api/v1/auth/signin - User sign in.
  * <strong>POST</strong> /api/v1/auth/forgot-password - Request password reset.
  * <strong>POST</strong> /api/v1/auth/reset-password - Reset password.
* **User Controller**
  * <strong>GET</strong> /api/v1/user/{id} - Get user by ID.
  * <strong>GET</strong> /api/v1/user/account/info - Get account information.
  * <strong>PATCH</strong> /api/v1/user/account/email - Change email.
  * <strong>PATCH</strong> /api/v1/user/account/password - Change password.
  * <strong>POST</strong> /api/v1/user/account/signout - Sign out.
  * <strong>POST</strong> /api/v1/user/account/verify - Verify account.
  * <strong>DELETE</strong> /api/v1/user/account - Delete account.
  * <strong>POST</strong> /api/v1/user/account/send-account-verification-code - Send verification code to verify email.
  * <strong>POST</strong> /api/v1/user/account/send-delete-account-verification-code - Send verification code to delete account.
* **Page Controller**
  * <strong>GET</strong> /api/v1/page/{url} - Get page by URL.
  * <strong>POST</strong> /api/v1/page - Create a new page.
  * <strong>PATCH</strong> /api/v1/page/{id} - Update page.
  * <strong>PATCH</strong> /api/v1/page/{id}/picture - Upload page picture.
  * <strong>DELETE</strong> /api/v1/page/{id} - Delete page.
  * <strong>POST</strong> /api/v1/page/{id}/social-link - Add social link.
  * <strong>PUT</strong> /api/v1/page/{id}/social-link/sort - Sort social links.
  * <strong>PATCH</strong> /api/v1/page/social-link/{id} - Update social link.
  * <strong>DELETE</strong> /api/v1/page/social-link/{id} - Delete social link.
  * <strong>POST</strong> /api/v1/page/{id}/custom-link - Add custom link.
  * <strong>PUT</strong> /api/v1/page/{id}/custom-link/sort - Sort custom links.
  * <strong>PATCH</strong> /api/v1/page/custom-link/{id} - Update custom link.
  * <strong>DELETE</strong> /api/v1/page/custom-link/{id} - Delete custom link.
* **Analytic Controller**
  * <strong>POST</strong> /api/v1/analytic/visit - Log a page visit.
  * <strong>POST</strong> /api/v1/analytic/social-link-click - Log a social link click.
  * <strong>POST</strong> /api/v1/analytic/custom-link-click - Log a custom link click.
  * <strong>GET</strong> /api/v1/analytic - Get page analytics by date range.

## Database Diagram
![linkplaza-db](https://github.com/user-attachments/assets/f916eb61-a129-4ee2-87fd-7cbfd784fe91)

