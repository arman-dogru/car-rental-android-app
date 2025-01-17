# 🚗 Bybloss-App

**Bybloss-App** is a full-featured **Android** application developed using **Kotlin** and **Firebase**, designed for **car rental agencies** to create and manage vehicle listings while enabling customers to book and purchase rental cars seamlessly.

---

## 📌 Features

- 🏢 **Car Rental Agency Portal**
  - Create and manage vehicle listings.
  - Update availability and pricing.
  - Track customer bookings.

- 👥 **Customer Features**
  - Browse available rental cars.
  - Make bookings and manage reservations.
  - Complete purchases securely.

- 🔥 **Backend with Firebase**
  - **Firestore Database** for real-time updates.
  - **Firebase Authentication** for secure login & sign-up.
  - **Cloud Storage** for vehicle images.

---

## 🚀 Installation & Setup

### **1️⃣ Clone the Repository**
```sh
git clone https://github.com/arman-dogru/Bybloss-App.git
cd Bybloss-App
```

### **2️⃣ Open in Android Studio**
- Launch **Android Studio**.
- Open the **Bybloss-App** project.

### **3️⃣ Configure Firebase**
- Add your **Google Services JSON file** (`google-services.json`) inside the `app/` folder.
- Enable **Firestore**, **Authentication**, and **Cloud Storage** in Firebase Console.

### **4️⃣ Build & Run the App**
Click **Run ▶️** in Android Studio or use:
```sh
./gradlew assembleDebug
```

---

## 🔧 Dependencies

Ensure you have these dependencies in `build.gradle`:
```gradle
dependencies {
    implementation 'com.google.firebase:firebase-firestore:23.0.3'
    implementation 'com.google.firebase:firebase-auth:21.0.1'
    implementation 'com.google.firebase:firebase-storage:20.0.0'
    implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
    implementation 'com.squareup.picasso:picasso:2.8'
}
```

---

## 📊 Database Structure

| Entity          | Description |
|----------------|------------|
| `Users`        | Stores customer and agency details. |
| `Cars`         | Vehicle listings, pricing, and availability. |
| `Bookings`     | Stores active and past reservations. |
| `Payments`     | Transaction details for completed purchases. |

🖼 **Database Schema**:
![Relations Diagram](Relations%20Diagram.png)

---

## 🏗 Future Enhancements

- **📍 Location-Based Search** – Allow customers to find nearby rentals.
- **💳 Payment Gateway Integration** – Secure online payments.
- **📅 Booking Calendar** – Improved UI for reservations.

---

## 🤝 Contributing

Contributions are welcome! Please fork the repository and submit a **pull request** with any improvements.

---

## 📜 License

This project is open-source and available under the **MIT License**.

---

### ⭐ **Enjoyed this project? Star this repository! 🌟**
