Markdown Tips
===============================
[Markdownで行こう！](https://gist.github.com/wate/7072365)

# ■　各種変換
## 1.MarkdownからHtmlへの変換
+ githubやatomなどで、Previewをしながら、markdownファイルを作成する
+ ブラウザのアドレスバーにて、以下を入力し、編集可能なhtml Editorに変身。
```
    data:text/html, <html contenteditable>
```
+ 作成済みのmarkdownファイルのPreview画面より、内容をコピーし、html Editorにペーストする。
+ htmlファイルとして保存する

## 2.htmlファイルから、wordファイルへの変換
+ htmlファイルを選択し、右クリックして、wordを開くを選択
+ wordにて適宜内容を修正し、wordファイルとして保存する


## 3. linuxにてmarkdonw-->html
```
1. install node package management
#yum install npm

2. install markdown-to-html
To use the command line utilities
#npm install markdown-to-html -g
To use the Markdown or GithubMarkdown classes in your project
#npm install markdown-to-html --save

3. Example Usage
#markdown test.md > test.html

```
