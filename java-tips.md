Java Tips
===

## 多次元配列
https://www.javadrive.jp/start/array/index7.html

## RSAとECDSA、署名生成と署名検証どっちが速い？
http://blog.livedoor.jp/k_urushima/archives/1721840.html  

まとめ 簡単にまとめると時間がかかる順に
- RSA署名の生成には非常に時間がかかる
- ECDSAの署名検証は署名生成よりほんの少し長く時間がかかる
- ECDSAの署名生成は普通に時間がかかる
- RSA署名の検証は非常に短時間であるECDSAの署名生成は普通に時間がかかる
- RSAでは鍵長が長くなるほどその傾向が顕著になる。
- ECDSAはRSAほどは鍵長が長くなる影響を受けない。
