<div align="center">
  <img src="todo_logo.png" alt="TodoApp Logo" width="200"/>
</div>

<h3 align="center">TodoApp</h3>

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#about-the-project">About The Project</a></li>
    <li><a href="#getting-started">Getting Started</a></li>
    <li><a href="#built-with">Built With</a></li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#tests">Tests</a></li>
    <li><a href="#user-stories">User Stories</a></li>
    <li><a href="#future-plans">Future Plans</a></li>
  </ol>
</details>

## About The Project

TodoApp is a full-stack Android application designed for everyday task management. It lets users effortlessly write down tasks, set deadlines, and mark them as completed. 

There are two main screens:
- **Active Todos Page**
- **Done Todos Page**

Users can seamlessly switch between the two, with full CRUD support for each todo.

---

## ✨ Features

- 📝 Create, edit, and delete todos
- ✅ Mark tasks as completed and view them separately
- 📅 Set deadlines for tasks
- 🔀 Smooth navigation between **Active Todos** and **Completed Todos**
- 🔍 Lightweight and fast with **Jetpack Compose**
- 🔬 Includes **UI integration tests** 

---

## 🚀 Getting Started

### Prerequisites

- Android Studio Flamingo+ (or compatible)
- Gradle 8+
- Kotlin 1.9+
- Emulator or physical device (Android 8.0+)

## Built with

---

### Android

![Kotlin](https://img.shields.io/badge/Kotlin-000000?style=for-the-badge&logo=Kotlin) </br>
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-000000?style=for-the-badge&logo=JetpackCompose) </br>
![RoomDB](https://img.shields.io/badge/Room-000000?style=for-the-badge&logo=sqlite) </br>
![Hilt](https://img.shields.io/badge/Hilt-000000?style=for-the-badge&logo=Dagger) </br>
![Coroutines](https://img.shields.io/badge/Coroutines-000000?style=for-the-badge&logo=kotlin) </br>

---

## Usage

1. Open the project in Android Studio
2. Sync Gradle and run the app on an emulator or physical device
3. Add todos, set deadlines, and mark as completed with the checkmark button
4. Navigate between active and done screens using the bottom navigation

---

## Tests

- **Unit tests** written with **Kotest**
- **ViewModel behavior** tested (setting title, description, deadlines, etc.)
- **Repository interactions** are mocked with **MockK**
- **Flow state emissions** tested using **Turbine**

🧪 These tests ensure ViewModel logic is working as expected.

> ✅ Integration tests are all pass

---

## User Stories

1. As a user, I want to write a task so I remember it.
2. As a user, I want to set a deadline so I don’t miss important dates.
3. As a user, I want to edit a task if details change.
4. As a user, I want to mark a task done once completed.
5. As a user, I want to view completed tasks on a separate page.

---

## Future Plans

🚀 **End-to-End Testing with Appium**

- Automate real device interactions like creating, updating, and completing todos
- Validate UI elements and transitions
- Simulate user behavior across Active and Done screens
- Generate test reports for QA reviews

> This will help ensure reliability and catch edge-case bugs from a user's point of view.

