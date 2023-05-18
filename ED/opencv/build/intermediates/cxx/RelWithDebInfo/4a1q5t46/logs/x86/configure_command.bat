@echo off
"C:\\Users\\arche\\AppData\\Local\\Android\\Sdk\\cmake\\3.22.1\\bin\\cmake.exe" ^
  "-HC:\\Users\\arche\\Documents\\GitHub\\ECE191-SmartGlasses\\Code Backup\\DE\\ED\\opencv\\libcxx_helper" ^
  "-DCMAKE_SYSTEM_NAME=Android" ^
  "-DCMAKE_EXPORT_COMPILE_COMMANDS=ON" ^
  "-DCMAKE_SYSTEM_VERSION=24" ^
  "-DANDROID_PLATFORM=android-24" ^
  "-DANDROID_ABI=x86" ^
  "-DCMAKE_ANDROID_ARCH_ABI=x86" ^
  "-DANDROID_NDK=C:\\Users\\arche\\AppData\\Local\\Android\\Sdk\\ndk\\23.1.7779620" ^
  "-DCMAKE_ANDROID_NDK=C:\\Users\\arche\\AppData\\Local\\Android\\Sdk\\ndk\\23.1.7779620" ^
  "-DCMAKE_TOOLCHAIN_FILE=C:\\Users\\arche\\AppData\\Local\\Android\\Sdk\\ndk\\23.1.7779620\\build\\cmake\\android.toolchain.cmake" ^
  "-DCMAKE_MAKE_PROGRAM=C:\\Users\\arche\\AppData\\Local\\Android\\Sdk\\cmake\\3.22.1\\bin\\ninja.exe" ^
  "-DCMAKE_LIBRARY_OUTPUT_DIRECTORY=C:\\Users\\arche\\Documents\\GitHub\\ECE191-SmartGlasses\\Code Backup\\DE\\ED\\opencv\\build\\intermediates\\cxx\\RelWithDebInfo\\4a1q5t46\\obj\\x86" ^
  "-DCMAKE_RUNTIME_OUTPUT_DIRECTORY=C:\\Users\\arche\\Documents\\GitHub\\ECE191-SmartGlasses\\Code Backup\\DE\\ED\\opencv\\build\\intermediates\\cxx\\RelWithDebInfo\\4a1q5t46\\obj\\x86" ^
  "-DCMAKE_BUILD_TYPE=RelWithDebInfo" ^
  "-BC:\\Users\\arche\\Documents\\GitHub\\ECE191-SmartGlasses\\Code Backup\\DE\\ED\\opencv\\.cxx\\RelWithDebInfo\\4a1q5t46\\x86" ^
  -GNinja ^
  "-DANDROID_STL=c++_shared"
