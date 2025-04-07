# 🧠 MeVo — Smart Medical Management with Face Recognition

**MeVo** is an Android-based healthcare management app that leverages **facial recognition** to streamline patient identification and improve clinical operations. Designed for hospitals and clinics, MeVo enables staff to efficiently manage **appointments**, **medicine inventory**, and **room allocations** — all in one intuitive mobile platform.

---

 🔍 Key Features

### 👤 Face Recognition
- Instantly recognize and retrieve patient records using facial recognition.
- Ensures accurate identification and eliminates manual record searching.

### 📅 Appointment Management
- View, add, or reschedule patient appointments.
- Assign doctors and departments for visits.

### 💊 Medicine Stock
- Keep track of medicine inventory.
- Add or remove stock and receive alerts when inventory is low.

### 🏥 Room Management
- Monitor room availability and allocate rooms to patients.
- Track occupancy and room statuses in real-time.

---

 📱 Installation
To install the MeVo app:

1. Clone the repository or download the APK:
   ```bash
   git clone https://github.com/sarvesh-x/MeVo_project.git
   ```
2. Locate the `app-debug.apk` file in the root directory.
3. Transfer the APK to android device
4. Enable Install from Unknown Sources in device

## 🛠️ Development Setup
- Open the project in Android Studio.
- Sync Gradle and install dependencies.
- Connect your emulator or Android device.
- Run the app directly from Android Studio.

## 🧱 Tech Stack

### 📱 Android (Frontend)
```bash
| Component         | Technology Used         |
|------------------|--------------------------|
| Language          | Java / Kotlin            |
| UI Design         | Android XML Layouts      |
| IDE               | Android Studio           |
| Communication     | RESTful API (HTTP)       |
| APK Packaging     | Gradle (Kotlin DSL)      |
```
### ☁️ Server (Backend)
```bash
| Component           | Technology Used                        |
|--------------------|----------------------------------------|
| Server Hosting      | Microsoft Azure                        |
| API Framework       | Flask (Python)                         |
| Face Recognition    | `face_recognition` Python library      |
| Database            | MongoDB (NoSQL)                        |
| API Communication   | RESTful APIs (JSON over HTTP)          |
| Data Handling       | PyMongo / JSON                         |
```
## 📂 Project Structure
```bash
MeVo_project/
├── app/
│   ├── src/
│   │   ├── java/           # Business logic
│   │   ├── res/            # Layouts & UI
│   │   └── AndroidManifest.xml
├── app-debug.apk           # Prebuilt APK
├── build.gradle.kts
└── settings.gradle.kts
```
---

🚀 Future Plans
- 📈 Admin dashboard with real-time analytics
- 🧬 Health ID integration (Aadhar or national systems)
- 🔔 Push notifications for appointments and stock alerts
- 🔐 Advanced user roles (Doctors, Staff, Admin)

##🤝 Contributing
Pull requests and feature suggestions are welcome!
```bash
git checkout -b feature/yourFeature
git commit -m "Add your feature"
git push origin feature/yourFeature
```

##📄 License
<br>This project is licensed under the MIT License.

✨ Built By
<br>Developed with ❤️ by sarvesh-x
<br>Empowering hospitals with face-powered intelligence.
