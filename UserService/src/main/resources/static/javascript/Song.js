

let SongsContainer=document.querySelector('.SongsContainer');
let inner=document.querySelector('.inner');
let Songssize=0;
let input =document.querySelector('.input')
let user_img =document.querySelector('.user_img');
let submit_btn=document.querySelector('.btn')
let Accountdetails=document.querySelector('.Account_details');

 let error=document.querySelector('.error');
 let searchContainer=document.querySelector('.search-btn');
 let btn=document.querySelector('.btn');
let prev=document.querySelector('.prev');
let next=document.querySelector('.next');
 let SongsObject=inner.getAttribute('Song');
 let Songs=JSON.parse(SongsObject);
 let pagesize=3;
 let page=0;
 

user_img.addEventListener('mouseover',()=>{
    
	Accountdetails.style.display='block';
	
})



Accountdetails.addEventListener('mouseover', () => {
    
    Accountdetails.style.display = 'block';
});

Accountdetails.addEventListener('mouseout', () => {
    
    Accountdetails.style.display = 'none';
});


			

let ztml="";
let html="";


 
for(let i=0;i<Songs.length;i++){
	html+=`
	
   <div class="card">
   <div class="card-body">
     <p>${Songs[i].song}</p>
                <p>${Songs[i].artist}</p>
                <img  class="Songimage" src="data:image/png;base64,${Songs[i].image}" alt="Song Image">
                 <audio controls class="audio "preload="metadata">
                    <source src="data:audio/mpeg;base64,${Songs[i].songFile}"  type="audio/mpeg">
                    Your browser does not support the audio element
                </audio>
  </div>
</div>

   `
   }
     SongsContainer.innerHTML=html;
     
     
     input.addEventListener('input',()=>{
		 error.style.display="none";
	 })
     
     btn.addEventListener("click",()=>{
		  
		 let rtml=''
		
		   
		   let res=filterSongs(input.value);
		
		   
		  if(input.value==""  ){
			  
			  
              
               error.style.display="block";

              			   
        return; 
        
           
			       
		  }
		 if( input.value!=null && res==false){
			 
			 error.style.display="none";
		
			        
			        rtml+=`<h2 class="empty_Songs" ><p>There are no songs to display<p></h2>`
			       
			        
			     	SongsContainer.innerHTML=rtml;
			     	
		
			 }
		
		  
	 })
     
    function filterSongs( value){
		ztml='';
	 const filteredSongs = Songs.filter(e => e.song.startsWith(value));
const Songssize = filteredSongs.length;

console.log(Songssize);

if (Songssize==0) {
     
	 return Songssize <0;
}
		 else if(Songssize>0){
			 
			 filteredSongs.forEach(e => {
			 console.log(e.song);
			ztml+=`
	
   <div class="card">
   <div class="card-body">
     <p>${e.song}</p>
                <p>${e.artist}</p>
                <img  class="Songimage" src="data:image/png;base64,${e.image}" alt="Song Image">
                 <audio controls class="audio" preload="metadata">
                    <source src="data:audio/mpeg;base64,${e.songFile}"  type="audio/mpeg">
                    Your browser does not support the audio element
                </audio>
  </div>
</div>

   `
		 })
		SongsContainer.innerHTML=ztml;
		
	}
	 return Songssize > 0;
	

}
	function fetchDataAndUpdateUI(page){
		
		if(page<0){
			prev.disabled=true;
			return;
		}
		
		let z="";
		const params = {
  page: page,
  size: pagesize
};

// Convert the parameters to a query string
const queryString = Object.keys(params)
  .map(key => encodeURIComponent(key) + '=' + encodeURIComponent(params[key]))
  .join('&');

// Specify the URL with the query string
const url = 'http://localhost:8082/songs?' + queryString
console.log(url);

      

		fetch(url).then(response=>response.json()).then(data=>{
			console.log(data[0]);
			
			 if (data.length ===0 ) {
                next.disabled=true; 
                return;
               
            }
			
			for(let i=0;i<data.length;i++){
			z+=` <div class="card">
   <div class="card-body">
     <p>${data[i].song}</p>
                <p>${data[i].artist}</p>
                <img  class="Songimage" src="data:image/png;base64,${data[i].image}" alt="Song Image">
                 <audio controls class="audio "preload="metadata">
                    <source src="data:audio/mpeg;base64,${data[i].songFile}"  type="audio/mpeg">
                    Your browser does not support the audio element
                </audio>
  </div>
</div>`
			
			}
			
             
                next.disabled = false;
                prev.disabled=false;
                	SongsContainer.innerHTML=z; // Enable the button if there are more elements to display
            
        })
        .catch(error => {
            console.error('Error occurred during fetch:', error);
            // Handle the error here if needed
       
		})
		
		  
		
		 
	}

   next.addEventListener("click",()=>{
	    page = page + 1;
	   fetchDataAndUpdateUI(page)
	  
   })

    prev.addEventListener("click",()=>{
		 page = page - 1;
	   fetchDataAndUpdateUI(page)
	  
   })
  