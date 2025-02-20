package com.comunus.ams.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.comunus.ams.model.FileModel;

@Service
public class FileService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean saveFile(String fileName, byte[] data) {
		String base64Data = Base64.getEncoder().encodeToString(data);
	    LocalDateTime currentDateTime = LocalDateTime.now();

        FileModel fileEntity = new FileModel();
        fileEntity.setFileName(fileName);
        fileEntity.setFileData(base64Data);
      // fileEntity.setCreated_date(currentDateTime);
       String sql ="Insert into filedata(file_name,file_data,created_date) values(?,?,?)";
       
       String truncateSql = "TRUNCATE TABLE filedata";
       
       try {
    	   
           jdbcTemplate.update(truncateSql);

    	   int update = jdbcTemplate.update(sql,fileEntity.getFileName(),fileEntity.getFileData(),currentDateTime);
           
           if(update >0)
           {
        	   return true;
           }
           else
           {
        	   return false;
           }
       }
       catch (Exception e) {
		// TODO: handle exception
    	   e.printStackTrace();
    	   return false;
	}
    }
	
	public List<FileModel> getFile(String fileName) {
	    List<FileModel> fm = new ArrayList<>();
	    try { 
	       //SELECT File_data FROM ams.filedata where file_name=
	    	String sql = "SELECT File_data FROM ams.filedata WHERE file_name = ?";
	        System.out.println("Executing SQL: " + sql + " with fileName: " + fileName); // Debugging log
	        fm = jdbcTemplate.query(sql, new Object[]{fileName}, BeanPropertyRowMapper.newInstance(FileModel.class));
	        return fm.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(fm);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return Collections.emptyList();
	    }
	}
}