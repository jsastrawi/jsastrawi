JSastrawi
=========
[![Build Status](https://travis-ci.org/jsastrawi/jsastrawi.svg?branch=master)](https://travis-ci.org/jsastrawi/jsastrawi)
[![Maven Central](https://img.shields.io/maven-central/v/com.andylibrian.jsastrawi/jsastrawi.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.andylibrian.jsastrawi%22%20AND%20a%3A%22jsastrawi%22)
[![Dependency Status](https://www.versioneye.com/user/projects/55b2c8d6643533001b0006b4/badge.svg?style=flat)](https://www.versioneye.com/user/projects/55b2c8d6643533001b0006b4)

JSastrawi is a collection of Natural Language Processing (NLP) tools for Bahasa Indonesia.
It is originally a java port of [Sastrawi Stemmer](https://github.com/sastrawi/sastrawi).

Components
----------

JSastrawi terdiri dari beberapa komponen yaitu:

#### Lemmatizer

*[Lemmatization](https://en.wikipedia.org/wiki/Lemmatisation)* ialah proses mengubah kata berimbuhan menjadi kata dasar. Seperti:

- menahan => tahan
- berbalas-balasan => balas

Tersedia demo pada [http://sastrawi.github.io](http://sastrawi.github.io)


Cara Install
------------

### Maven

```xml
<dependency>
    <groupId>com.andylibrian.jsastrawi</groupId>
    <artifactId>jsastrawi</artifactId>
    <version>0.1</version>
</dependency>
```

### Gradle

```
compile 'com.andylibrian.jsastrawi:jsastrawi:0.1'
```

### Jar

- https://github.com/jsastrawi/jsastrawi/releases


Cara Menggunakan
----------------

### Lemmatizer

```java
// Mulai setup JSastrawi, cukup dijalankan 1 kali

// JSastrawi lemmatizer membutuhkan kamus kata dasar
// dalam bentuk Set<String>
Set<String> dictionary = new HashSet<String>();

// Memuat file kata dasar dari distribusi JSastrawi
// Jika perlu, anda dapat mengganti file ini dengan kamus anda sendiri
InputStream in = Lemmatizer.class.getResourceAsStream("/root-words.txt");
BufferedReader br = new BufferedReader(new InputStreamReader(in));

String line;
while ((line = br.readLine()) != null) {
    dictionary.add(line);
}

Lemmatizer lemmatizer = new DefaultLemmatizer(dictionary);
// Selesai setup JSastrawi
// lemmatizer bisa digunakan berkali-kali

System.out.println(lemmatizer.lemmatize("memakan"));
System.out.println(lemmatizer.lemmatize("meminum"));
System.out.println(lemmatizer.lemmatize("bernyanyi"));
```

Lisensi
--------

[Lisensi JSastrawi](https://github.com/jsastrawi/jsastrawi/blob/master/LICENSE) adalah MIT License (MIT).

Produk ini mengandung software yang dibangun oleh Apache Software Foundation ([http://www.apache.org](http://www.apache.org)).

Produk ini menggunakan pustaka CLI dari Apache Commons project ([http://commons.apache.org](http://commons.apache.org)).

Produk ini mengandung kamus kata dasar yang berasal dari Kateglo dengan lisensi [CC-BY-NC-SA 3.0](http://creativecommons.org/licenses/by-nc-sa/3.0/), kemudian dilakukan beberapa perubahan.

