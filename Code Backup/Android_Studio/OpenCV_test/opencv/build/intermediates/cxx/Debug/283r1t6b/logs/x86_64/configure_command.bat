@echo off
"C:\\Users\\arche\\AppData\\Local\\Android\\Sdk\\cmake\\3.22.1\\bin\\cmake.exe" ^
  "-HC:\\Users\\arche\\Documents\\GitHub\\ECE191-SmartGlasses\\Code Backup\\Android_Studio\\OpenCV_test\\opencv\\libcxx_helper" ^
  "-DCMAKE_SYSTEM_NAME=Android" ^
  "-DCMAKE_EXPORT_COMPILE_COMMANDS=ON" ^
  "-DCMAKE_SYSTEM_VERSION=24" ^
  "-DANDROID_PLATFORM=android-24" ^
  "-DANDROID_ABI=x86_64" ^
  "-DCMAKE_ANDROID_ARCH_ABI=x86_64" ^
  "-DANDROID_NDK=C:\\Users\\arche\\AppData\\Local\\Android\\Sdk\\ndk\\23.1.7779620" ^
  "-DCMAKE_ANDROID_NDK=C:\\Users\\arche\\AppData\\Local\\Android\\Sdk\\ndk\\23.1.7779620" ^
  "-DCMAKE_TOOLCHAIN_FILE=C:\\Users\\arche\\AppData\\Local\\Android\\Sdk\\ndk\\23.1.7779620\\build\\cmake\\android.toolchain.cmake" ^
  "-DCMAKE_MAKE_PROGRAM=C:\\Users\\arche\\AppData\\Local\\Android\\Sdk\\cmake\\3.22.1\\bin\\ninja.exe" ^
  "-DCMAKE_LIBRARY_OUTPUT_DIRECTORY=C:\\Users\\arche\\Documents\\GitHub\\ECE191-SmartGlasses\\Code Backup\\Android_Studio\\OpenCV_test\\opencv\\build\\intermediates\\cxx\\Debug\\283r1t6b\\obj\\x86_64" ^
  "-DCMAKE_RUNTIME_OUTPUT_DIRECTORY=C:\\Users\\arche\\Documents\\GitHub\\ECE191-SmartGlasses\\Code Backup\\Android_Studio\\OpenCV_test\\opencv\\build\\intermediates\\cxx\\Debug\\283r1t6b\\obj\\x86_64" ^
  "-DCMAKE_BUILD_TYPE=Debug" ^
  "-BC:\\Users\\arche\\Documents\\GitHub\\ECE191-SmartGlasses\\Code Backup\\Android_Studio\\OpenCV_test\\opencv\\.cxx\\Debug\\283r1t6b\\x86_64" ^
  -GNinja ^
  "-DANDROID_STL=c++_shared"
