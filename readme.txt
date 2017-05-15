rm -rf build/ app/build/ utilslib/build/ 
./gradlew clean build install bintrayUpload

# add tag
# git tag -a 1.1 -m “1.1”
# git push --tag

# delete tag
#git push origin --delete tag 1.1


