<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!doctype html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>アカウント設定 - 2要素認証example</title>
  </head>
  <body>
    <h1>アカウント設定</h1>
    <form method="POST">
      <p>
        <label>
          <input type="radio" name="twoFactorAuthN" value="true"
            <c:if test="${twoFactorAuthN}"> checked</c:if>>
          2要素認証あり
        </label>
        <label>
          <input type="radio" name="twoFactorAuthN" value="false"
            <c:if test="${not twoFactorAuthN}"> checked</c:if>>
          2要素認証なし
        </label>
      </p>
      <p>
        <button type="submit">設定する</button>
      </p>
    </form>
    <nav>
      <ul>
        <li><a href="${application.contextPath}/">ホームへ戻る</a></li>
      </ul>
    </nav>
  </body>
</html>
