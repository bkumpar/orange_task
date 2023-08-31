package com.bkumpar.orange.restservice;

import java.util.HashMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bkumpar.orange.entities.Entity;
import com.bkumpar.orange.entities.Note;
import com.bkumpar.orange.entities.XbrlNodeLeaf;
import com.bkumpar.orange.extractor.XbrlDataExtractor;
import com.bkumpar.orange.entities.LinkBetweenSubjects;
import com.bkumpar.orange.entities.Metadata;



@RestController
public class RestServiceController {

	private String extractData(String key) {

		HashMap<String, Entity> testData = new HashMap<>();
		
		// test 1
		Note note = new Note(new XbrlNodeLeaf(42, new Metadata("PROG_NOTE", "NUMBER(5,0)", true))
						, new XbrlNodeLeaf("X1", new Metadata("TYPE_NOTE", "(2 CHARS)", false))
						, new XbrlNodeLeaf("ooooooooo", new Metadata("TEXT_NOTE", "VARCHAR2(64000 BYTES)", false)));
		testData.put("1", note);

		// test 2
		note = new Note(new XbrlNodeLeaf(27.d, new Metadata("PROG_NOTE", "NUMBER(5,0)", true))
						, new XbrlNodeLeaf("20230831", new Metadata("TYPE_NOTE", "VARCHAR2(8 CHARS)-YYYYMMDD", false))
						, new XbrlNodeLeaf("Note about this entity", new Metadata("TEXT_NOTE", "VARCHAR2(64000 BYTES)", false)));
		
		testData.put("2", note);

		// test 3
		LinkBetweenSubjects link = new LinkBetweenSubjects(new XbrlNodeLeaf(53, new Metadata("PROGG_SOGG_PRIM", "NUMBER(5,0)", true))
														, new XbrlNodeLeaf(125, new Metadata("PROGG_SOGG_SEC", "NUMBER(5,0)", true))
														, new XbrlNodeLeaf("PPL23", new Metadata("TYPE_LINK_PEOPLE_PEOPLE", "VARCHAR2(2 CHAR)", false))
														, new XbrlNodeLeaf("Description", new Metadata("DESCRIPTION", "VARCHAR2(50 CHAR)", false)));
		testData.put("3", link);

		try {
			return XbrlDataExtractor.extract(testData.get(key));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		}
	}
	
	@GetMapping("/extract")
	public Extract extract(@RequestParam(value = "index", defaultValue = "1") String index) {
		
		return new Extract(extractData(index));
	}
}
