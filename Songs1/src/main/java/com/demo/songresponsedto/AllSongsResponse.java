package com.demo.songresponsedto;

import java.util.List;

import com.demo.songs.Songs;

public class AllSongsResponse {






		
		  private List<Songs>songs;
		    
		    
		   
		    private int totalPages;
		    
		    private int currentPage;
		    
		    private Long userId;

			public AllSongsResponse() {
				
			}

			
			
			
			
			public AllSongsResponse(List<Songs> songs, int totalPages, int currentPage, Long userId) {
				super();
				this.songs = songs;
				this.totalPages = totalPages;
				this.currentPage = currentPage;
				this.userId = userId;
			}





			public List<Songs> getSongs() {
				return songs;
			}

			public void setSongs(List<Songs> songs) {
				this.songs = songs;
			}

			public int getTotalPages() {
				return totalPages;
			}

			public void setTotalPages(int totalPages) {
				this.totalPages = totalPages;
			}

			public int getCurrentPage() {
				return currentPage;
			}

			public void setCurrentPage(int currentPage) {
				this.currentPage = currentPage;
			}





			public Long getUserId() {
				return userId;
			}





			public void setUserId(Long userId) {
				this.userId = userId;
			}

			

			
			

			
			
	}


