CLSPH=.:`ls lib/*.jar | xargs echo | sed 's/ /:/g'`
# echo $CLSPH
java -server -Xmx64m -classpath $CLSPH:Bill-1.0-SNAPSHOT.jar com.simpledev.bill.BillApplication > stdout.log & echo $! > dddd.pid