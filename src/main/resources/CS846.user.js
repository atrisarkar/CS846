// ==UserScript==
// @name        Test
// @namespace   http://github.com/elasticsearch/elasticsearch/pull
// @include     https://github.com/*
// @require     http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js
// @version     1
// @grant       GM_xmlhttpRequest
// ==/UserScript==
$(document).ready(showAlert);






function showAlert() {
  var t =  (document.getElementsByClassName("tabnav-tabs")[0]).children[2].cloneNode(true);
  console.log (t);
 
  var related_bucket_content = document.getElementById("toc").cloneNode(true);
  related_bucket_content.setAttribute("id","toc_related_files");
  related_bucket_content.removeChild(related_bucket_content.children[0]);
  related_bucket_content.children[0].setAttribute("id","warning_label");
  related_bucket_content.children[1].setAttribute("id","file_list");
  
  var related_bucket = document.getElementById("files_bucket").cloneNode(false);
  related_bucket.setAttribute("id","related_bucket");
  related_bucket.appendChild(related_bucket_content);
  var parent_of_bucket = document.getElementsByClassName("view-pull-request clearfix js-issues-results")[0].appendChild(related_bucket);
  
  
  
  t.children[0].setAttribute("data-container-id","related_bucket");
  var ih = t.children[0].innerHTML.replace("Files changed","Related Files");
  t.children[0].innerHTML = ih;
  document.getElementsByClassName("tabnav-tabs")[0].appendChild(t);
  
  
  GM_xmlhttpRequest({
  method: "GET",
  headers: {"Accept": "application/json"},
  url: "http://localhost:8080/CS846_Web/rest/relatedfile",
  onload: function(response) {
    updateData(response);
  }
  });
  //alert(ih);
}

function updateData(response) {
  //alert($().jquery);
  var json_response = response.responseText;
  var response_object = JSON.parse(json_response);
  
  var warning_label = document.getElementById("warning_label");
  warning_label.innerHTML = "You might want to review the following files for potential impact";
  warning_label.style = "background-color:#e8f0f8";
  document.getElementById("toc_related_files").setAttribute("class","details-collapse table-of-contents js-details-container open");
  
  $("#file_list > li").children("a").text(response_object.filename);
 
}