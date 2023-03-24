result=$(git pull)
id=$(ps ax | grep chatgpt_telegram_bot-0.0.1-SNAPSHOT.jar | grep -v grep |  awk '{print $1}' )
if [[ ("$result" == "Already up to date." && "$id" != "") ]]; then
    echo "No updates. Exiting..."
    exit
fi
./build.sh &&

kill -9 $id
java -jar target/chatgpt_telegram_bot-0.0.1-SNAPSHOT.jar > /tmp/app.log &