<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!doctype html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>ホーム - 2要素認証example</title>
  </head>
  <body>
    <h1>ホーム</h1>
    <p>こんにちは、${fn:escapeXml(name)}さん！</p>
    <nav>
      <ul>
        <li><a href="${application.contextPath}/account_settings">アカウント設定</a></li>
      </ul>
    </nav>
  </body>
</html>
