Shibboleth SP Tips
====

## ■ Shibboleth-SP構築後の確認用ツール

### 1.本ツールの目的
　Shibboleth-SP構築後、APLと連携する際のHTTP-Headerパラメータ、
Attributeパラメータを確認するためのツールである。

### 2.利用手順
#### 2.1 testフォルダをTomcatのwebapps配下へ配置
 配置例：
  /usr/local/tomcat/webapps/test/httpheader.jsp

  ```
  <%
	StringBuffer sb = new StringBuffer();
	java.util.Enumeration headernames = request.getHeaderNames();
	while (headernames.hasMoreElements()) {
		String name = (String) headernames.nextElement();
		java.util.Enumeration headervals = request.getHeaders(name);
		while (headervals.hasMoreElements()) {
			String val = (String) headervals.nextElement();
			sb.append(name);
			sb.append(":::: ");
			sb.append(val);
			sb.append("<br>");
		}
	}

	String headerStr = sb.toString();

	StringBuffer sb1 = new StringBuffer();

	java.util.Enumeration attrnames = request.getAttributeNames();
	while (attrnames.hasMoreElements()) {
		String name = (String) attrnames.nextElement();
		java.util.Enumeration attrvals = request.getHeaders(name);
		while (attrvals.hasMoreElements()) {
			String val = (String) attrvals.nextElement();
			sb1.append(name);
			sb1.append(":::: ");
			sb1.append(val);
			sb1.append("<br>");
		}
	}

	String attrStr = sb1.toString();

%>

<html>
<header>
<title>http header viewer</title>
</header>
<body>
HTTP Header List:
<br />
<p><%=headerStr%></p>
<br />
HTTP request Attribute List:
<br />
<p><%=attrStr%></p>

</body>
</html>
  ```

#### 2.2 ApacheとTomcat連携の設定
 AJPを利用する場合の設定例：  
 ```
 /etc/httpd/conf/httpd.conf
  ------
 <Location /test >
     ProxyPass ajp://localhost:8009/test/
  </Location>
```

#### 2.3 Shibboleth認証の設定
/etc/httpd/conf.d/shib.confに以下を追加　　
```
#for test
<Location /test>
  AuthType shibboleth
  ShibRequestSetting requireSession 1
  require shib-session
  require valid-user
  ShibUseHeaders On
</Location>
```

#### 2.4 Apacheの再起動
```
 #service httpd restart
 ```

### 3.ツールの利用
 https://{fqdn}/test/httpheader.jsp
