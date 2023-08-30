# train-schedule
## Part of MRT-K

Background:
MRT-K adalah untuk membantu pengguna untuk melakukan perjalanan menggunakan Mass Rapid Transit (MRT) di wilayah Kivotos.
Overview: 
- Aplikasi ini berbentuk web app dengan UI yang difokuskan untuk mobile. Contoh UI seperti ini adalah kitabisa.com.
- Terdapat satu jenis akun, yaitu akun pengguna.
- Terdapat beberapa jenis layanan kereta, yaitu Local dan Limited Express.
- Pengguna dapat melihat jadwal kereta pada rentang waktu yang ditentukan di suatu stasiun.
- Pengguna dapat membeli tiket yang berupa QR Code dengan saldo aplikasi.
- Tarif tiket dihitung per kilometer dan terlihat saat pengguna hendak membeli tiket.
- Tarif layanan Limited Express lebih tinggi 25% dari layanan Local untuk jarak yang sama.
- Tiket yang sudah dibeli valid sampai sehari setelah hari pembelian tiket pada pukul 02.00.
- Pengguna dapat melihat tipe dan validitas tiket melalui aplikasi.
- Pengguna dapat melihat riwayat pembelian tiket.
- Pengguna dapat melakukan top up saldo aplikasi dengan beberapa nominal top up yang disediakan.
- Tarif per kilometer, jarak antar stasiun, dan peta rute akan dikabarkan setelah topik diambil.

Fitur: 
- Otorisasi dan otentikasi.
- Melihat jadwal kereta pada rentang waktu tertentu di suatu stasiun.
- Pembelian dan riwayat pembelian tiket.
- Pembayaran tiket dan top up saldo aplikasi.
- Menghitung tarif dari satu stasiun ke stasiun lainnya.

Tech Stack:
- BE: Spring Boot
- FE: Thymeleaf

Service : train-schedule  

Purpose:  

Microservice ini bertugas untuk menampilkan jadwal kereta pada rentang waktu tertentu di suatu stasiun. Jadwal tersebut memiliki atribut line (String), station (String), trainType (String), time(String). Design pattern yang akan digunakan di microservice ini adalah facade pattern.
Technologies used:
- Programming Language : Java
- Frameworks and Libraries: Spring Boot
- Database: PostgreSQL
- Deployment Technology: Google Cloud
