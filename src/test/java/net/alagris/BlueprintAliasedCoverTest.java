package net.alagris;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

public class BlueprintAliasedCoverTest {

	Group<String> gr;
	Blueprint<GlobalCnfg> blueprint;

	public BlueprintAliasedCoverTest() throws JsonProcessingException, IOException, DuplicateIdException, UndefinedAliasException {
		BlueprintTypedLoader<String, GlobalCnfg> loader = new BlueprintTypedLoader<String, GlobalCnfg>(
				BlueprintAliasedCoverTest.class, String.class, GlobalCnfg.class);
		blueprint = loader.load(TestConstants.ALIASED);
		gr = loader.make(blueprint);
	}

	
	@Test
	public void parsing() {
		HashMap<String, ArrayList<Node>> nodes = blueprint.collectByAlias();
		ArrayList<Node> preprocessors = nodes.get("preprocessors");
		assertNotNull(find(preprocessors,"Preprocessor-id0"));
		assertNotNull(find(preprocessors,"Preprocessor-id1"));
		assertNotNull(find(preprocessors,"Preprocessor-id2"));
		ArrayList<Node> truecasers = nodes.get("truecasers");
		assertNotNull(find(truecasers,"Lowercase-id"));
		assertNotNull(find(truecasers,"Truecaser-id"));
		assertNotNull(find(truecasers,"Uppercase-id"));
		
	}

	private Node find(ArrayList<Node> aliased, String id) {
		for(Node a:aliased) {
			if(id.equals(a.getId())) {
				return a;
			}
		}
		return null;
	}


	@Test
	public void injection() {
	}

	@Test
	public void process() {
	}

	@Test
	public void destroy() throws Exception {
		gr.close();
	}
}
