Self CA Readme
==
## 0. premise
 all commands are tested in centos 7.0

## 1. generate keypair and csr file. you'd better do these works at your site and send only csr file to CA.
the following is just sample commands to do these on CA server.
cd /etc/pki/CA/private

openssl genrsa 2048 > fqdn.key

openssl req -new -key fqdn.key > fqdn.csr

## 2. copy csr file to CA server to apply a certification file
cd /etc/pki/CA

openssl ca -in private/fqdn.csr -out certs/fqdn.cer -md sha256
#ca.pem pwd ch....it

## 3. confirm the certification file.
openssl x509 -text < certs/fqdn.cer

## 4. convert detailed certification file to pem format
openssl x509 < certs/fqdn.cer > certs/fqdn.cer.pem

## 5. if you need subjectAltName
### 5.1 prepair san_openssl.cnf for san csr request
cp /etc/pki/tls/openssl.cnf /etc/pki/tls/san_openssl.cnf  
add alt_names section refer to [this url.](https://rms-digicert.ne.jp/howto/csr/openssl.html)
    ## DNS.1=foo.com
    ## DNS.2=bar.com
    ## IP.1=10.10.10.10

### 5.2 request a subjectAltName csr file
openssl req -config /etc/pki/tls/san_openssl.cnf -new -key fqdn.key > fqdn.csr

### 5.3 confirm
openssl req -text < fqdn.csr

### 5.4 apply a certification file
prepair openssl.cnf, uncomment copy_extensions = copy  
openssl ca -in private/fqdn.csr -out certs/fqdn.cer -md sha256

## 6. java jks
### 6.1 generate rsk from keytool
    keytool -genkeypair -keyalg rsa -keysize 2048 -validity 3650 -keystore me.keystore

### 6.2 request a csr file
    keytool -certreq -alias mykey -file me.csr -keystore me.keystore

### 6.3 apply a certification file
    openssl ca -in private/me.csr -out certs/me.cer -md sha256

### 6.4 import ca cert to jvm cacerts and certificate file to keystore
    keytool -import -trustcacerts -file ca.cer -keystore cacers
    keytool -import -trustcacerts -file me.cer -keystore me.keystore

## 7. java jks need a san name
generate a san certs from openssl, change it to p12, and to jks.
```
openssl pkcs12 -export -keypbe PBE-SHA1-3DES -certpbe PBE-SHA1-3DES -export -in certs/fqdn.cer -inkey private/fqdn.key -out certs/fqdn.p12

keytool -importkeystore -destkeystore me.jks -deststoretype JKS -srcstoretype PKCS12 -srckeystore fqdn.p12
```  

## ref:
### Is a wildcard SAN certificate possible?  https://stackoverflow.com/questions/21489525/is-a-wildcard-san-certificate-possible
