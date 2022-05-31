/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.idsia.mario.engine.level;

import ch.idsia.mario.engine.sprites.Enemy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vv
 */
public class LevelParser {
    
	public static final int BUFFER_WIDTH = 15; // This is the extra space added at the start and ends of levels
	
    /**
     *
     * @param args
     */
    public LevelParser(){
        
    }
    
    
    /*"tiles" : {
    0    "X" : ["solid","ground"],
    1    "S" : ["solid","breakable"],
    2    "-" : ["passable","empty"],
    3    "?" : ["solid","question block", "full question block"],
    4    "Q" : ["solid","question block", "empty question block"],
    5    "E" : ["enemy","damaging","hazard","moving"],
    6    "<" : ["solid","top-left pipe","pipe"],
    7    ">" : ["solid","top-right pipe","pipe"],
    8    "[" : ["solid","left pipe","pipe"],
    9    "]" : ["solid","right pipe","pipe"],
    10   "o" : ["coin","collectable","passable"]
    
    // These last two were not present in the json description from VDLC, but were present in the data
    
    11   "B" : Top of a Bullet Bill cannon, solid
    12   "b" : Body/support of a Bullet Bill cannon, solid
    */
    
    public Level test(){
        Level level = new Level(202,14);
        level.setBlock(1, 13, (byte) 9);
        level.setBlock(2, 13, (byte) 9);
        level.setBlock(3, 13, (byte) 9);
        level.setBlock(4, 13, (byte) 9);
        level.setBlock(5, 13, (byte) 9);
        level.setBlock(6, 13, (byte) 9);
        level.setBlock(7, 13, (byte) 9);
        
        return level;
    }

    /**
     * This method doesn't seem to be used anywhere. I guess it was completely
     * replaced by the createLevelJson method?
     * @param filename
     * @return
     */
    public static Level createLevelASCII(String filename)
    {
    	//Read in level representation
    	ArrayList<String> lines = new ArrayList<String>();
    	try {
    		File file = new File(filename);
    		FileReader fileReader = new FileReader(file);
    		BufferedReader bufferedReader = new BufferedReader(fileReader);
    		String line;
    		while ((line = bufferedReader.readLine()) != null) {
    			lines.add(line);
    			System.out.println(line);
    		}
    		fileReader.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}

    	int width = lines.get(0).length();
    	int height = lines.size();
    	// For a buffer at both the start and the end of each level
    	int extraStones = BUFFER_WIDTH;
    	Level level = new Level(width+2*extraStones,height);

    	//Set Level Exit
    	//Extend level by that
    	level.xExit = width+extraStones+1;
    	level.yExit = height-1;

    	for(int i=0; i<extraStones; i++){
    		level.setBlock(i, height-1, (byte) 9);
    	}

    	for(int i=0; i<extraStones; i++){
    		level.setBlock(width+i+extraStones, height-1, (byte) 9);
    	}

    	//set Level map
    	for(int i=0; i<height; i++){
    		for(int j=0; j<lines.get(i).length(); j++){
    			String code = String.valueOf(lines.get(i).charAt(j));
    			if("E".equals(code)){
    				//set Enemy
    				//new SpriteTemplate(type, boolean winged)
    				level.setSpriteTemplate(j+extraStones, i, new SpriteTemplate(Enemy.ENEMY_GOOMBA, false));
    				//System.out.println("j: "+j+" i:"+i);
    				//set passable tile: everything not set is passable
    			}else{
    				int encoded = codeParserASCII(code);
    				if(encoded !=0){
    					level.setBlock(j+extraStones, i, (byte) encoded);
    					//System.out.println("j: "+j+" i:"+i+" encoded: "+encoded);
    				}
    			}
    		}
    	}
    	return level;
    }

    /**
     * This method doesn't seem to be used anywhere. I guess it was completely
     * replaced by the createLevelJson method?
     * @param filename
     * @return
     */
    public static Level createLevelMarioAI(String filename)
    {
        //Read in level representation
        ArrayList<String> lines = new ArrayList<String>();
        try {
            File file = new File(filename);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
                System.out.println(line);
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int width = lines.get(0).length();
        int height = lines.size();
        // For a buffer at both the start and the end of each level
        int extraStones = BUFFER_WIDTH;
        Level level = new Level(width+2*extraStones,height);

        //Set Level Exit
        //Extend level by that
        level.xExit = width+extraStones+1;
        level.yExit = height-1;

        for(int i=0; i<extraStones; i++){
            level.setBlock(i, height-1, (byte) 9);
        }

        for(int i=0; i<extraStones; i++){
            level.setBlock(width+i+extraStones, height-1, (byte) 9);
        }

        //set Level map
        for(int i=0; i<height; i++){
            for(int j=0; j<lines.get(i).length(); j++){
                String code = String.valueOf(lines.get(i).charAt(j));
                if("E".equals(code)){
                    //set Enemy
                    //new SpriteTemplate(type, boolean winged)
                    level.setSpriteTemplate(j+extraStones, i, new SpriteTemplate(Enemy.ENEMY_GOOMBA, false));
                    //System.out.println("j: "+j+" i:"+i);
                    //set passable tile: everything not set is passable
                }else{
                    int encoded = codeParserASCII(code);
                    if(encoded !=0){
                        level.setBlock(j+extraStones, i, (byte) encoded);
                        //System.out.println("j: "+j+" i:"+i+" encoded: "+encoded);
                    }
                }
            }
        }
        return level;
    }

    public static Level createLevelJson(List<List<Integer>> input)
    {
    	int width = input.get(0).size();
    	int height = input.size();
    	int extraStones = BUFFER_WIDTH;
    	Level level = new Level(width+2*extraStones,height);

        //Set Level Exit
        //Extend level by that
        level.xExit = width+extraStones+1; // Push exit point over by 1 so that goal post does not overlap with other level sprites
        level.yExit = height-1;
        
        for(int i=0; i<extraStones; i++){
            level.setBlock(i, height-1, (byte) 9);
        }
        for(int i=0; i<extraStones; i++){
            level.setBlock(width+i+extraStones, height-1, (byte) 9);
        }
        
        //set Level map
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                int code = input.get(i).get(j);
                if(5==code){
                    //set Enemy
                    //new SpriteTemplate(type, boolean winged)
                    level.setSpriteTemplate(j+extraStones, i, new SpriteTemplate(Enemy.ENEMY_GOOMBA, false));
                    //System.out.println("j: "+j+" i:"+i);
                    //set passable tile: everything not set is passable
                }else{
                    int encoded = codeParser(code);
                    if(encoded !=0){
                        level.setBlock(j+extraStones, i, (byte) encoded);
                        //System.out.println("j: "+j+" i:"+i+" encoded: "+encoded);
                    }
                }
            }
        }
                
        return level;
    }

    public static String createMarioAILevelJson(List<List<Integer>> input)
    {
        int width = input.get(0).size();
        int height = input.size();
        int extraStones = BUFFER_WIDTH;
        int level[][] = new int[width+2*extraStones][height];


        for(int i=0; i<extraStones; i++){
            level[i][height-1] = 1;
        }
        for(int i=0; i<extraStones; i++){
            level[width+i+extraStones][height-1] = 1;
        }

        level[0][height-2] = 14;
//        level[0][height-5] = 11;

        level[width+ 2*extraStones -1][height-2] = 15;

        //set Level map
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                int code = input.get(i).get(j);
                level[j+extraStones][i] = code+1;//BAI: add one to made 0 to "-"
            }
        }

        String level_represent = "";

        for(int i=0; i< height; i++){
            for(int j=0; j< width+2*extraStones; j++){
                level_represent += codeRepresent(level[j][i]);
            }
            level_represent += "\n";
        }

        return level_represent;
    }

    public static String saveLeveltxt(Level level) {
        String str = "";
        for(int j=0; j < level.height; j++){
            for(int i=0; i < level.width; i++){
                switch (level.represent[i][j]){
                    case 0: str += "-"; break;
                    case 2: str += "E"; break;
                    case 9: str += "X"; break;
                    case 10: str += "<"; break;
                    case 11: str += ">"; break;
                    case 14: str += "B"; break;
                    case 16: str += "S"; break;
                    case 21: str += "?"; break;
                    case 26: str += "["; break;
                    case 27: str += "]"; break;
                    case 34: str += "o"; break;
                    case 46: str += "b"; break;
                }
            }
            str += "\n";
        }
        return str;
    }

     
    public static int codeParser(int code){
        int output = 0;
        switch(code){
            case 0: output = 9; break; //rocks
            case 1: output = 16; break; //"S" : ["solid","breakable"]
            case 3: output = 21; break; //"?" : ["solid","question block", "full question block"]
            case 4: output = 21; break; //"?" : ["solid","question block", "full question block"]
            case 6: output = 10; break; //"<" : ["solid","top-left pipe","pipe"]
            case 7: output = 11; break; //">" : ["solid","top-right pipe","pipe"]
            case 8: output = 26; break; //"[" : ["solid","left pipe","pipe"]
            case 9: output = 27; break; //"]" : ["solid","right pipe","pipe"]
            case 10: output = 34; break; //"o" : ["coin","collectable","passable"]
            // Bullet Bill cannons not described in VDLC json, but were present in the data
            case 11: output = 14; break; //"B" : Top of a Bullet Bill cannon, solid
            // There may be a problem here: VGLC uses "b" to represent what is either sprite 30 or 46 in Infinite Mario
            case 12: output = 46; break; //"b" : Body/support of a Bullet Bill cannon, solid
            default: output=0; break; //"-" : ["passable","empty"],  "Q" : ["solid","question block", "empty question block"],  "E" : ["enemy","damaging","hazard","moving"],
        }
        return output;
    }

    public static String codeRepresent(int code){
        String output = "-";
        switch(code){
            case 0: output = "-"; break; //rocks
            case 1: output = "X"; break; //rocks
            case 2: output = "S" ; break; //"S" : ["solid","breakable"]
            case 4: output = "!"; break; //"?" : ["solid","question block", "full question block"]
            case 5: output = "!"; break; //"?" : ["solid","question block", "full question block"]
            case 6: output = "E"; break; //'E' : Goomba
            case 7: output = "<"; break; //"<" : ["solid","top-left pipe","pipe"]
            case 8: output = ">" ; break; //">" : ["solid","top-right pipe","pipe"]
            case 9: output = "[" ; break; //"[" : ["solid","left pipe","pipe"]
            case 10: output = "]"; break; //"]" : ["solid","right pipe","pipe"]
            case 11: output = "o" ; break; //"o" : ["coin","collectable","passable"]
            // Bullet Bill cannons not described in VDLC json, but were present in the data
            case 12: output = "B"; break; //"B" : Top of a Bullet Bill cannon, solid
            // There may be a problem here: VGLC uses "b" to represent what is either sprite 30 or 46 in Infinite Mario
            case 13: output = "b"; break; //"b" : Body/support of a Bullet Bill cannon, solid
            case 14: output = "M"; break; // Mario start
            case 15: output = "F"; break; // Mario finish line, not having it will force the engine to end at x = levelWidth and the first ground floor.
            default: output= "-"; break; //"-" : ["passable","empty"],  "Q" : ["solid","question block", "empty question block"],  "E" : ["enemy","damaging","hazard","moving"],
        }
        return output;
    }
    
    public static int codeParserASCII(String code){
        int output = 0;
        switch(code){
            case "X": output = 9; break; //rocks
            case "S": output = 16; break; //"S" : ["solid","breakable"]
            case "?": output = 21; break; //"?" : ["solid","question block", "full question block"]
            case "Q": output = 21; break; //"?" : ["solid","question block", "full question block"]
            case "<": output = 10; break; //"<" : ["solid","top-left pipe","pipe"]
            case ">": output = 11; break; //">" : ["solid","top-right pipe","pipe"]
            case "[": output = 26; break; //"[" : ["solid","left pipe","pipe"]
            case "]": output = 27; break; //"]" : ["solid","right pipe","pipe"]
            case "o": output = 34; break; //"o" : ["coin","collectable","passable"]
            // Bullet Bill cannons not described in VDLC json, but were present in the data
            case "B": output = 14; break; //"B" : Top of a Bullet Bill cannon, solid
            // There may be a problem here: VGLC uses "b" to represent what is either sprite 30 or 46 in Infinite Mario
            case "b": output = 46; break; //"b" : Body/support of a Bullet Bill cannon, solid
            default: output=0; break; //"-" : ["passable","empty"],  "Q" : ["solid","question block", "empty question block"],  "E" : ["enemy","damaging","hazard","moving"],
        }
        return output;
    }

    public static int codeParserMarioAI(String code){
        int output = 0;
        switch(code){
            case "X": output = 9; break; //rocks
            case "S": output = 16; break; //"S" : ["solid","breakable"]
            case "?": output = 21; break; //"?" : ["solid","question block", "full question block"]
            case "Q": output = 21; break; //"?" : ["solid","question block", "full question block"]
            case "<": output = 10; break; //"<" : ["solid","top-left pipe","pipe"]
            case ">": output = 11; break; //">" : ["solid","top-right pipe","pipe"]
            case "t": output = 26; break; //"[" : ["solid","left pipe","pipe"]
            case "]": output = 27; break; //"]" : ["solid","right pipe","pipe"]
            case "o": output = 34; break; //"o" : ["coin","collectable","passable"]
            // Bullet Bill cannons not described in VDLC json, but were present in the data
            case "B": output = 14; break; //"B" : Top of a Bullet Bill cannon, solid
            // There may be a problem here: VGLC uses "b" to represent what is either sprite 30 or 46 in Infinite Mario
            case "b": output = 46; break; //"b" : Body/support of a Bullet Bill cannon, solid
            default: output=0; break; //"-" : ["passable","empty"],  "Q" : ["solid","question block", "empty question block"],  "E" : ["enemy","damaging","hazard","moving"],
        }
        return output;
    }

}
