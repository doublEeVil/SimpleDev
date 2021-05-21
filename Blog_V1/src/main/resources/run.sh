CLSPH=.:`ls lib/*.jar | xargs echo | sed 's/ /:/g'`
# echo $CLSPH
java -server -Xmx64m -classpath $CLSPH:Blog_V1-1.0-SNAPSHOT.jar com.simpledev.blog.BlogApp > stdout.log & echo $! > dddd.pid