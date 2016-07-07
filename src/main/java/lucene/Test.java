/**
 * 
 */
package lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

/**
 * @author zyl
 * @date 2016年6月22日
 * 
 */
public class Test {

	public static void main(String[] args) throws Exception {
		Analyzer analyzer = new StandardAnalyzer();

	    // Store the index in memory:
	    Directory directory = new RAMDirectory();
	    // To store an index on disk, use this instead:
	    //Directory directory = FSDirectory.open("/tmp/testindex");
	    IndexWriterConfig config = new IndexWriterConfig(analyzer);
	    IndexWriter iwriter = new IndexWriter(directory, config);
	    
	    Document doc = new Document();
	    String text = "aaa bbb eee fff.";
	    doc.add(new Field("fieldname", text, TextField.TYPE_STORED));
	    iwriter.addDocument(doc);
	    Document doc2 = new Document();
	    String text2 = "aaa bbb ccc ddd .";
	    doc2.add(new Field("fieldname", text2, TextField.TYPE_STORED));
	    iwriter.addDocument(doc2);
	    Document doc3 = new Document();
	    String text3 = "ccc ddd eee fff.";
	    doc3.add(new Field("fieldname", text3, TextField.TYPE_STORED));
	    iwriter.addDocument(doc3);
	    Document doc4 = new Document();
	    String text4 = "我是中国人 .";
	    doc4.add(new Field("fieldname", text4, TextField.TYPE_STORED));
	    iwriter.addDocument(doc4);
	    Document doc5 = new Document();
	    String text5 = "我是陕西人.";
	    doc5.add(new Field("fieldname", text5, TextField.TYPE_STORED));
	    iwriter.addDocument(doc5);
	    Document doc6 = new Document();
	    String text6 = "计算机真是一个好东西 .";
	    doc6.add(new Field("fieldname", text6, TextField.TYPE_STORED));
	    iwriter.addDocument(doc6);
	    Document doc7 = new Document();
	    String text7 = "我要去超市买点东西.";
	    doc7.add(new Field("fieldname", text7, TextField.TYPE_STORED));
	    iwriter.addDocument(doc7);
	    
	    iwriter.close();
	    
	    // Now search the index:
	    DirectoryReader ireader = DirectoryReader.open(directory);
	    IndexSearcher isearcher = new IndexSearcher(ireader);
	    // Parse a simple query that searches for "text":
	    QueryParser parser = new QueryParser("fieldname", analyzer);
//	    Query query = parser.parse("aaa");
	    Query query = parser.parse("计算机");
	    ScoreDoc[] hits = isearcher.search(query, 1000).scoreDocs;
	    // Iterate through the results:
	    for (int i = 0; i < hits.length; i++) {
	      Document hitDoc = isearcher.doc(hits[i].doc);
	      System.out.println("查询到的文档内容是："+hitDoc.get("fieldname"));
	    }
	    ireader.close();
	    directory.close();
	}
}
