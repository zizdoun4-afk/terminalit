# TerminalIT

<p align="center">
  <img src="app/src/main/res/mipmap-xxxhdpi/ic_launcher.png" alt="TerminalIT Logo" width="128"/>
</p>

<p align="center">
  <strong>A modern, powerful, and secure SSH client for Android, built with Jetpack Compose.</strong>
</p>

---

## 📱 About TerminalIT

**TerminalIT** is a robust SSH terminal application designed for Android devices. Built entirely with Kotlin and Jetpack Compose, it provides system administrators, developers, and power users with a seamless and native experience for managing remote servers on the go.

Whether you're troubleshooting a server from your phone, running a quick script, or managing infrastructure, TerminalIT offers a desktop-grade terminal emulator right in your pocket.

---

## ✨ Key Features

### 🛡️ Core SSH & Security
* **Robust SSH Connections:** Powered by the proven JSch library for stable and secure connections.
* **Authentication Options:** Supports both Password and Private Key authentication.
* **Host Key Verification:** Built-in security to accept, reject, and monitor server host keys (prevents MITM attacks).
* **Biometric Security:** Lock your saved connection profiles with device biometrics (Fingerprint/Face Unlock) to ensure your credentials are encrypted and safe.

### 💻 Advanced Terminal Emulator
* **ANSI Escape Codes:** Full support for colors, cursor movements, and terminal formatting.
* **Dynamic Resizing:** The terminal grid automatically calculates rows/columns based on your screen size and font size.
* **Pinch-to-Zoom:** Easily increase or decrease the font size by simply pinching the screen.
* **"Clear Terminal" Button:** Instantly wipes the local display and sends `Ctrl+L` to redraw the shell prompt.
* **Terminal Bell Support:** Reacts to the standard `\u0007` bell character with a subtle device vibration and a background notification if the app is minimized.

### ⌨️ Input & Productivity
* **Hardware Keyboard Support:** Full support for physical keyboards (Bluetooth/USB) including modifiers (`Ctrl+C`, `Ctrl+D`), `Esc`, `Tab`, and Arrow keys.
* **Extra Keys Bar:** An on-screen floating bar providing quick access to essential terminal keys (`Esc`, `Tab`, `Ctrl`, `Up`, `Down`, `Left`, `Right`, etc.) when using the virtual keyboard.
* **Textarea Command Mode:** A dedicated multi-line editor mode. Perfect for drafting and reviewing long, complex commands before sending them to the server.

### 🎨 Modern UI/UX
* **100% Jetpack Compose:** A fluid, modern, and reactive user interface.
* **Dark Mode Native:** Deep dark background (`#0D0D11`) optimized for AMOLED screens to save battery and reduce eye strain.
* **Profile Management:** Save multiple server profiles for 1-tap connections.

---

## 🛠️ Tech Stack

TerminalIT leverages the modern Android development ecosystem:
* **Language:** Kotlin
* **UI Toolkit:** Jetpack Compose & Material 3
* **Architecture:** MVVM (Model-View-ViewModel) + Clean Architecture principles
* **Dependency Injection:** Hilt / Dagger
* **Asynchronous Programming:** Kotlin Coroutines & Flow
* **SSH Protocol:** JSch
* **Local Storage:** Encrypted SharedPreferences & DataStore
* **Security:** Android BiometricPrompt & Keystore API

---

## 🚀 Getting Started

### Prerequisites
* Android Studio (Koala or newer recommended)
* JDK 17+
* Android device or emulator running API 26 (Android 8.0) or higher.

### Building the Project
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/terminalit.git
   ```
2. Open the project in **Android Studio**.
3. Let Gradle sync and download all dependencies.
4. Click **Run** (`Shift + F10`) or use the command line:
   ```bash
   ./gradlew installDebug
   ```

---

## 📸 Screenshots

*(Add screenshots here once the repository is live! Recommended shots: The Profile List, The Terminal Screen with htop running, The Textarea Input Mode, and the Host Key Verification prompt.)*

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!
Feel free to check the [issues page](https://github.com/yourusername/terminalit/issues).

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
