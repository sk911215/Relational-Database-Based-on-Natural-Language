package finalproject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FinalProject {
	
	static List listAcc = null;
	
	public static void main(String[] args) throws IOException {
		String dicTrash = "trash.txt";
		String dicAll = "all.txt";
		String dicZero = "attribute.txt";
		String dicOne = "gender.txt";
		String dicTwo = "department.txt";
		String dicThree = "grade.txt";
		String dicFour = "action.txt";
		String dicRange = "range.txt";
		String colOne = "gender";
		String colTwo = "department";
		String colThree = "grade";
		String colFour = "name";
		String table = "student";
		List<String> [] gender = null ;
		String department = null;
		int grade = 0;
		String action="select";
		boolean isColOne = true;
		boolean isColTwo = true;
		boolean isColThree = true;
		boolean isColFour = true;
		
		System.out.println("Input a sentence:");
		Scanner in = new Scanner(System.in);
		String nlp = in.nextLine();
		
		//timer
		long startTime=System.nanoTime();
				
		//get char 
		String[] arr=nlp.split("[^a-zA-Z=<>]+");
				
		//get int 
		String[] arrDigit=nlp.split("[^0-99999999]+");
		
		//delete trash word	
		listAcc = delTrash(arr, dicTrash);
		System.out.println(listAcc);
		
		List listAll = find(listAcc, dicAll);
		System.out.println(listAll);
		
		//find action
		List listCol4 = find(listAcc, dicFour);
		System.out.println(listCol4);
		if(listCol4.size()==0) {
			action = "select";
		}
		else {
			action = (String) listCol4.get(0);
		}
		System.out.println(listAcc);
		
		//find attribute for char word
		List listCol0 = find(listAcc, dicZero);
		System.out.println(listCol0);
		System.out.println(listAcc);
		
		
		//find gender
		List listCol1 = find(listAcc, dicOne);
		System.out.println(listCol1);
		System.out.println(listAcc);
		
		
		//find department
		List listCol2 = find(listAcc, dicTwo);
		System.out.println(listCol2);
		System.out.println(listAcc);

		//find range		
		List listColRange = find(listAcc, dicRange);;
		System.out.println(listColRange);
		System.out.println(listAcc);
		
		//find name		
		List listCol3 = listAcc;
		System.out.println(listCol3);
		System.out.println(listAcc);
		
		//find int
		List listCol5 = findInt(arrDigit, dicThree);

		System.out.println(listCol5);
	
//		boolean isNameWhere = false, isNameSel = false, isGenderWhere = false, isGenderSel = false, isDepWhere = false, isDepSel = false, isGradeWhere = false, isGradeSel = false; 
//		String sel = null;
		String[] attribute = {"name", "gender", "department", "grade"};
		
		if (action.equalsIgnoreCase("select"))	{
			List[] valueList = {listCol3, listCol1, listCol2, listCol5};
			Boolean [][] selWhereResult = new Boolean[4][2];
			int numSel=0, numWhere = 0;
			//initialize selWhereResult
			for (int i=0; i<attribute.length; i++){
				for (int j=0; j<2; j++){
					selWhereResult[i][j]=false;
				}
			}
			//calculate selWhereResult
			for (int i=0; i<attribute.length; i++) {
				selWhereResult[i]= selWhere(listCol0, valueList[i], attribute[i]);
//				sel = attribute[i];
				if (selWhereResult[i][0]){
					numSel++;
				}
				if (selWhereResult[i][1]){
					numWhere++;
				}
			}
			if (numSel==0)
				selWhereResult[0][0] = true;
			
			//calculate stringWhere
			String[] stringWhereResult = new String[4];
//			stringWhereResult[3].replaceAll(null, "aaa");
//			System.out.println(stringWhereResult[3]);
			for (int i=0; i<4; i++) {
				if(i<3) {
					if(valueList[i].size()>0) {
						stringWhereResult[i]= stringWhere(attribute[i], null, valueList[i], selWhereResult[i][1], false);
					}
					else
						stringWhereResult[i]= null;
				}
				else{
					if(valueList[i].size()>0)
						stringWhereResult[i]= stringWhere(attribute[i], listColRange, valueList[i], selWhereResult[i][1], true);
					else
						stringWhereResult[i]= null;
				}
			}
			
			String aSel = "SELECT ", aWhere = "", a = null;
			for(int i=0; i<4; i++){
				if (selWhereResult[i][0]) {
						aSel = aSel + attribute[i] + ", ";
					}
				if (stringWhereResult[i]!=null) {
					if (i<1){
						aWhere = aWhere + "where " + stringWhereResult[i];
					}
					else{
						aWhere = aWhere + " where and " + stringWhereResult[i];
					}
				}
			}
			if (aSel.endsWith(", ")) {
				aSel = aSel.substring(0, aSel.length()-2);
			}
		
			
			a = aSel + " FROM " + table + " " + aWhere;
			
			if (listAll.size()>0) {
				a = "SELECT " + "*" + " FROM " + table + " where " + stringWhereResult[0] + " and " + stringWhereResult[1] + " and " + stringWhereResult[2] + " and " + stringWhereResult[3];
			}
			System.out.println(a);
//			String[] arr1=a.split("[^a-zA-Z0-9=><,*]+");
//			List b = null;
//			for (int i=0;i<arr1.length;i++) {
//				b.add(arr1[i]);
//			}
//			for (int i=0; i<4; i++){
//				b = delSel(selWhereResult[i][0], b);
//			}			
			List b = delWhere(a);
			System.out.println(b);
			String c = (String) b.get(0);
			for (int i1=0; i1<b.size()-1; i1++) {
				c = c + " " + b.get(i1+1);
			}

//			if (c.contains("where where")) {
//				c = aWhere.replace("where where", "where") ;
//			}
			for (int i=0; i<2; i++) {
				if (c.contains("where and")) {
					if(!c.contains("student where"))
						c = c.replace("where and", "where") ;
					else
						if(c.contains("student where and"))
							c = c.replace("student where and", "student where") ;
						else
							c = c.replace("where and", "and") ;
				}
			}
//			if (c.contains("student and")) {
//				c = aWhere.replace("student and", "student where") ;
//			}
//			if (c.contains("student and")) {
//				c = aWhere.replace("student and", "student where") ;
//			}
			System.out.println(c);
			long endTime=System.nanoTime(); //获取结束时间  
			System.out.println("running time： "+(endTime-startTime)+"ns");   
			
			//feedback
			System.out.println("Correct? Please enter yes or no Or re-enter correct words");
			Scanner in1 = new Scanner(System.in);
			String feedback = in1.nextLine();
			String changeName = stringWhereResult[0];
			if (listAcc.size()>1){
				while (feedback.equals("no")){
					String midName = changeName;
					changeName = feedback(feedback, selWhereResult[0][1], listAcc);
					System.out.println(changeName);
					if (changeName != null) {
						c = c.replace(midName, changeName);
						System.out.println(c);
						System.out.println(listAcc.size());
						System.out.println("Correct? Please enter yes or no Or re-enter correct words");
						in1 = new Scanner(System.in);
						feedback = in1.nextLine();
						System.out.println(feedback);
						
//						continue;
					}
					else{
						changeName = midName;
						System.out.println(listAcc.size());
						c = c.replace(changeName, "");
						if (c.contains("where  and"))
							c = c.replace("where  and", "where");
						System.out.println(c);
						break;
					}
				}
			}
			else {
				System.out.println(listAcc.size());
				c = c.replace(changeName, "");
				if (c.contains("where  and"))
					c = c.replace("where  and", "where");
				System.out.println(c);
			
			}
		}
		
	}
	
	

	private static String feedback(String feedback, Boolean isNameWhere, List list) {
		String changeName = "name=";
		if (isNameWhere){
			if (list.size()>1){
				list.remove(0);
				changeName = changeName + list.get(0);
			}
			else
				return null;
			return changeName;
		}
		else
			return null;
	}



	public static String stringWhere(String attribute, List listColRange, List listVal, boolean isWhere, boolean isGrade) throws IOException {
		String result = null;
		if (isGrade){
			if (isWhere) {
				if (!listColRange.contains("not")) {
					if (listColRange.contains("larger")||listColRange.contains("bigger")||listColRange.contains("higher")||listColRange.contains(">")||listColRange.contains("above")) {
						result = "grade>" + listVal.get(0);
					}
					else if (listColRange.contains("smaller")||listColRange.contains("lower")||listColRange.contains("<")||listColRange.contains("below")) {
						result = "grade<" + listVal.get(0);
					}
					else if (listColRange.contains("between")||listColRange.contains("range")) {
						result = "grade BETWEEN " + listVal.get(0) + " AND " + listVal.get(1);
					}
					else {
						result = "grade=" + listVal.get(0);
					}
				}
				else {
					if (listColRange.contains("larger")||listColRange.contains("bigger")||listColRange.contains("higher")||listColRange.contains(">")||listColRange.contains("above")) {
						result = "grade<=" + listVal.get(0);
					}
					else if (listColRange.contains("smaller")||listColRange.contains("lower")||listColRange.contains("<")||listColRange.contains("below")) {
						result = "grade>=" + listVal.get(0);
					}
					else if (listColRange.contains("between")||listColRange.contains("range")) {
						result = "grade BETWEEN " + 0 + " AND " + listVal.get(0) + " OR grade BETWEEN " + listVal.get(1) + " AND " + 100;
					}
					else {
						result = "grade!=" + listVal.get(0);	
					}
				}
			}
		}
		else {
			if (isWhere) {
				if (listVal.size()>0) {
					result = attribute + "=" + listVal.get(0);
				}
			}
		}
		return result;
	}
	
	public static Boolean[] selWhere(List listAtt, List listVal, String a) throws IOException {
		Boolean [] result = new Boolean[2];
		result[0] = false;
		result[1] = false;
		if (listAtt.contains(a)) {
			if (listVal.size()>0) {
				result[1] = true;
			}
			else {
				result[0] = true;
			}
		}
		else {
			if (listVal.size()>0) {
				result[1] = true;
			}		
		}
		return result;
	}
	
	
	
	public static List delWhere(String a) throws IOException {
		boolean isMatch = false;
		List list = new ArrayList();
		String[] arr=a.split("[^a-zA-Z0-9=><,*]+");
		for (int i=0;i<arr.length;i++) {
			isMatch = false;
			if (arr[i].equals("null")) {
				isMatch = true;
			}
			if (arr[i].equals("where")) {
				if (arr[i+1].equals("null")) {
					isMatch = true;
				}
			}
			if (arr[i].equals("and")) {
				if (arr[i-1].equals("null")) {
					isMatch = true;
				}
//				if (arr[i+1].equals("where")) {
					if (arr[i+1].equals("null")) {
						isMatch = true;
					}
//				}
			} 						
			if (!isMatch) {
				list.add(arr[i]);
			}	
		}
		if (list.get(list.size()-1).equals("where")){
			list.remove(list.size()-1);
		}
	    return list;
	}

	public static List delTrash(String[] inputArr, String dic) throws IOException {
		boolean isMatch = true;
		List list = new ArrayList();
		for (int i=0;i<inputArr.length;i++) {
			isMatch = checkMatch(inputArr[i], dic);
			if (!isMatch) {
				
				list.add(inputArr[i]);
				
			}
		}
	    return list;
	}
	
	
	public static List find(List listAcc, String dic) throws IOException {
		boolean isMatch = false;
		List list = new ArrayList();
		for (int i=0;i<listAcc.size();i++) {
			String word = (String) listAcc.get(i);
			isMatch = checkMatch(word, dic);
			if (isMatch) {
				list.add(word);
				listAcc.remove(word);
				i--;
			}
		}
		return list;
	}
	
	public static List findInt(String[] inputArr, String dic) throws IOException {
		boolean isMatch = false;
		List list = new ArrayList();
		for (int i=0;i<inputArr.length;i++) {
			if (inputArr[i].length()>=1) {
				isMatch = checkMatch(inputArr[i], dic);
				if (isMatch) {
					list.add(inputArr[i]);
				}
			}
		}
	    return list;
	}
	
		
	public static boolean checkMatch(String word, String dictionary) throws IOException {
		 boolean flag=false;  
		  BufferedReader buf=new BufferedReader(new FileReader(dictionary));  
		  StringBuffer sbuf=new StringBuffer();//缓冲字符串  
		  String line=null;  
		  while((line=buf.readLine())!=null){  
			  sbuf.append(line);//追加到缓冲字符串中  
		  }  
		  buf.close();//读取结束  
		  word = word.toLowerCase();
		  Pattern expression=Pattern.compile(word);//定义正则表达式匹配单词  
		  String string1=sbuf.toString().toLowerCase();//转换成小写  
		  //String string1=sbuf.toString();
		  Matcher matcher=expression.matcher(string1);//定义string1的匹配器  
		  //boolean flag=false;
		  while(matcher.find()){//是否匹配单词  
			  flag = true;
			  return flag;
		  }
		  return flag;  
	}
}
