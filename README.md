# Task: Build a Kotlin Android Calculator App & Push to GitHub with APK Release CI/CD

## Objective
Create a fully functional Android Calculator App written in Kotlin, push it to a new GitHub repository using the provided PAT, and include a GitHub Actions workflow that automatically compiles and publishes the APK to GitHub Releases on every push to main.

---

## GitHub Credentials
- **PAT Token:** REDACTED
- **GitHub Username:** RD7890
- **Target Repo Name:** KotlinCalculatorApp (create it via GitHub API if it doesn't exist)

---

## App Requirements

Build a standard Android Calculator App with the following:

### Features
- Basic arithmetic: Addition, Subtraction, Multiplication, Division
- Clear (C) and Delete (⌫) buttons
- Decimal point support
- Display shows current input and running result
- Handle divide-by-zero gracefully

### Tech Stack
- Language: Kotlin
- Min SDK: 21
- Target SDK: 34
- Build Tool: Gradle (Kotlin DSL preferred)
- UI: XML layouts (no Jetpack Compose — keep it simple and compilable)

### Project Structure
```
KotlinCalculatorApp/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/calculator/
│   │   │   └── MainActivity.kt
│   │   ├── res/
│   │   │   ├── layout/activity_main.xml
│   │   │   └── values/strings.xml
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
├── gradle/wrapper/gradle-wrapper.properties
└── .github/
    └── workflows/
        └── build-release.yml
```

---

## GitHub Actions Workflow (`build-release.yml`)

Create a workflow that:

1. Triggers on every push to `main`
2. Sets up JDK 17
3. Grants execute permission to `gradlew`
4. Runs `./gradlew assembleDebug`
5. Uploads the compiled APK as a GitHub Release artifact with a version tag (e.g., `v1.0-build-{run_number}`)

### ⚠️ Critical Note for Manus
> **DO NOT attempt to compile or run Gradle/Android builds locally inside your VM environment.** Android SDK compilation is resource-intensive and WILL fail in a constrained cheap VM. The entire build process must be delegated to GitHub Actions — your job is ONLY to write correct code, configure the project files properly, and push them to GitHub. Let GitHub's hosted runners handle all APK compilation.

### Workflow File Content
```yaml
name: Build & Release APK

on:
  push:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3

      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'

      - name: Grant execute permission for gradlew
        run: chmod +x gradlew

      - name: Build Debug APK
        run: ./gradlew assembleDebug

      - name: Create GitHub Release
        uses: softprops/action-gh-release@v1
        with:
          tag_name: v1.0-build-${{ github.run_number }}
          files: app/build/outputs/apk/debug/*.apk
        env:
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
```

---

## Push Instructions for Manus

1. Use the GitHub REST API or `git` CLI with the provided PAT to:
   - Create the repository (public) via `POST https://api.github.com/user/repos`
   - Use `RD7890` as the authenticated user
   - Initialize with all project files in a single commit to `main`
2. Set the default branch to `main`
3. Use the PAT directly in the `Authorization: Bearer` header for all GitHub API calls
4. After the repo is created and code is pushed, GitHub Actions will handle everything else automatically

---

## Deliverables Expected from Manus
- All source code files written correctly
- Gradle files properly configured (no sync errors)
- GitHub repo created and pushed under account `RD7890`
- Confirmation that the workflow YAML is present at `.github/workflows/build-release.yml`
- Share the final GitHub repo URL: `https://github.com/RD7890/KotlinCalculatorApp`

> Do not simulate or mock any outputs. Actually create the files and push to GitHub.
