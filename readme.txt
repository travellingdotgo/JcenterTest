rm -rf build/ app/build/ utilslib/build/ 
./gradlew clean build install bintrayUpload


# git tag -a 1.1 -m “v1.1”
# 1.1.*
