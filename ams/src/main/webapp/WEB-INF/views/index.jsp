<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Upload Multiple Files</title>
	
	<style>
	    .download-link {
	        display: block;
	        margin-top: 20px;
	        padding: 5px;
	        background-color: #4CAF50;
	        color: white;
	        text-align: center;
	        text-decoration: none;
	        border-radius: 5px;
	    }

	    .download-link:hover {
	        background-color: #45a049;
	    }
	</style>

	<style>
	body {
	    font-family: Arial, sans-serif;
	    background-color: #f4f4f4;
	    margin: 0;
	    padding: 20px;
	}

	.container {
	    max-width: 600px;
	    margin: auto;
	    background: white;
	    padding: 20px;
	    border-radius: 5px;
	    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
	}

	h1 {
	    text-align: center;
	}

	.file-upload {
	    margin: 20px 0;
	}

	input[type="file"] {
	    display: none;
	}

	label {
	    display: inline-block;
	    background: #007BFF;
	    color: white;
	    padding: 10px 20px;
	    border-radius: 5px;
	    cursor: pointer;
	    text-align: center;
	}

	button {
	    display: block;
	    width: 100%;
	    padding: 10px;
	    background: #28a745;
	    color: white;
	    border: none;
	    border-radius: 5px;
	    cursor: pointer;
	}

	button:hover {
	    background: #218838;
	}

	#fileList {
	    margin-top: 20px;
	}

	.file-item {
	    padding: 5px;
	    border: 1px solid #ccc;
	    margin: 5px 0;
	    border-radius: 4px;
	}
	.modal {
	    display: none; /* Hidden by default */
	    position: fixed; /* Stay in place */
	    z-index: 1; /* Sit on top */
	    left: 0;
	    top: 0;
	    width: 100%; /* Full width */
	    height: 100%; /* Full height */
	    overflow: auto; /* Enable scroll if needed */
	    background-color: rgb(0,0,0); /* Fallback color */
	    background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
	}

	.modal-content {
	    background-color: #fefefe;
	    margin: 15% auto; /* 15% from the top and centered */
	    padding: 20px;
	    border: 1px solid #888;
	    width: 80%; /* Could be more or less, depending on screen size */
	}

	.close {
	    color: #aaa;
	    float: right;
	    font-size: 28px;
	    font-weight: bold;
	}

	.close:hover,
	.close:focus {
	    color: black;
	    text-decoration: none;
	    cursor: pointer;
	}


	 </style>
    <script defer>
        document.addEventListener('DOMContentLoaded', () => {
            document.getElementById('fileInput').addEventListener('change', function() {
                const fileList = document.getElementById('fileList');
                fileList.innerHTML = ''; // Clear previous list

                for (let i = 0; i < this.files.length; i++) {
                    const fileItem = document.createElement('div');
                    fileItem.className = 'file-item';
                    fileItem.textContent = this.files[i].name;
                    fileList.appendChild(fileItem);
                }
            });

            document.getElementById('uploadForm').addEventListener('submit', async function(event) {
                event.preventDefault(); // Prevent default form submission

                const formData = new FormData(this);
                try {
                    const response = await fetch('/ams/v1/upload', {
                        method: 'POST',
                        body: formData
                    });

                    if (response.ok) {
                        const data = await response.json();
                        console.log('Upload successful:', data);
                        // Reload the page
						alert('File saved in this location: C:\\Attendance');
						// Check if the response contains the download link
						                   if (data[0].downloadLink) {
						                       // Create a download link button and append it to the page
						                       const downloadButton = document.createElement('a');
						                       downloadButton.href = data[0].downloadLink;
						                       downloadButton.textContent = 'Download the uploaded Excel file';
						                       downloadButton.className = 'download-link';
						                       // Add an event listener to reload the page after download is triggered
						                        downloadButton.addEventListener('click', function() {
						                            // Delay the reload slightly to ensure the download starts
						                            setTimeout(function() {
						                                window.location.reload(); // Reload the page after download starts
						                            }, 1000); // Delay for 1 second (adjustable)
						                        });
						                       document.body.appendChild(downloadButton);
						                   }
                       //window.location.reload();
                    } else {
                        console.error('Upload failed:', response.statusText);
						alert('Failed to save File');

                    }
                } catch (error) {
                    console.error('Error uploading files:', error);
                }
            });
        });
    </script>
</head>
<body>
    <div class="container">
        <h1>Upload Multiple Files</h1>
        <form id="uploadForm" action="/upload" method="POST" enctype="multipart/form-data">
            <div class="file-upload">
                <input type="file" name="files" id="fileInput" multiple required>
                <label for="fileInput">Choose files</label>
            </div>
            <button type="submit">Upload</button>
			<!--<a href="#" id="openModal" title="Pop Up">Pop Up</a>-->
        </form>
		<div id="myModal" class="modal">
		    <div class="modal-content">
		        <span class="close">&times;</span>
		        <h2>Your Content Here</h2>
		        <p>This is the content you wanted to display in the modal.</p>
		    </div>
		</div>
        <div id="fileList"></div>
    </div>
</body>
<script>
	// Get the modal
	var modal = document.getElementById("myModal");

	// Get the link that opens the modal
	var link = document.getElementById("openModal");

	// Get the <span> element that closes the modal
	var span = document.getElementsByClassName("close")[0];
	
	window.onload = function() {
	      
		fetchData();
		   
		   
		   
	   }
	
	// Function to call the API
	async function fetchData() {
	    try {
	        const response = await fetch('https://api.postalpincode.in/pincode/400072'); // Replace with your API URL
	        if (!response.ok) {
	            throw new Error('Network response was not ok');
	        }
	        const data = await response.json();
	        // Process the data (for example, display it in the modal)
	        document.querySelector('.modal-content p').textContent = JSON.stringify(data); // Customize as needed
			console.log('Data is ' , data);
			    } catch (error) {
	        console.error('There has been a problem with your fetch operation:', error);
	    }
	}

	link.onclick = function(event) {
	    event.preventDefault(); // Prevent the default anchor click behavior
	    fetchData(); // Call the API when the modal is opened
	    modal.style.display = "block"; // Show the modal
	}


	// When the user clicks on <span> (x), close the modal
	span.onclick = function() {
	    modal.style.display = "none";
	}

	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
	    if (event.target == modal) {
	        modal.style.display = "none";
	    }
	}

	</script>
</html>
