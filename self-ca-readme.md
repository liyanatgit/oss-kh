Self CA Readme
==

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
