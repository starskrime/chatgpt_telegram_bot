result=$(git pull)
if [ "$result" == "Already up to date." ]; then
    echo "No updates. Exiting..."
    exit
fi
./build.sh &&
id=$(ps ax | grep chatgpt_telegram_bot-0.0.1-SNAPSHOT.jar | grep -v grep |  awk '{print $1}' )
kill -9 $id
java -jar target/chatgpt_telegram_bot-0.0.1-SNAPSHOT.jar &