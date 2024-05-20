
document.addEventListener( "DOMContentLoaded", () => {

   // Set Default dropzone options
   // ---------------------------------------------------------------------------------
   Dropzone.options.demoDropzone = { // camelized version of the `id`
      paramName: "file", // The name that will be used to transfer the file
      maxFilesize: 2, // MB
      autoProcessQueue: false,
      //parallelUploads: 5,
      //uploadMultiple: true
   };



   // Simple dropzone
   // ---------------------------------------------------------------------------------
   const dropzone = new Dropzone("#_dm-dropzoneSimple");



   // Custom style
   // ---------------------------------------------------------------------------------
   const previewNode = document.querySelector( "#_dm-dropzoneTemplate" );
   const previewTemplate = previewNode.parentNode.innerHTML;

   previewNode.id = "";
   previewNode.parentNode.removeChild(previewNode);

   const uplodaBtn = document.querySelector( "#_dm-dropzoneCustomUploadBtn" );
   const removeBtn = document.querySelector( "#_dm-dropzoneCustomRemoveBtn" );


   const customStyleDz = new Dropzone(document.body, { // Make the whole body a dropzone
      url: "/target-url", // Set the url
      thumbnailWidth: 50,
      thumbnailHeight: 50,
      parallelUploads: 5,
      previewTemplate: previewTemplate,
      autoQueue: false, // Make sure the files aren"t queued until manually added
      previewsContainer: "#_dm-dropzonePreviews", // Define the container to display the previews
      clickable: "._dm-dropzoneFileButton" // Define the element that should be used as click trigger to select files.
   });


   customStyleDz.on( "addedfile", file => {
      // Hookup the button
      uplodaBtn.removeAttribute( "disabled" );
      removeBtn.removeAttribute( "disabled" );
   });


   // Update the total progress bar
   customStyleDz.on( "totaluploadprogress", progress => {
      // document.querySelector( "#_dm-dropzoneProgress .progress-bar" ).style.width = `${ progress }%`;
   });


   customStyleDz.on( "sending", file => {
      // Show the total progress bar when upload starts
      // document.querySelector( "#_dm-dropzoneProgress" ).style.opacity = "1";
   });

   // Hide the total progress bar when nothing"s uploading anymore
   customStyleDz.on( "queuecomplete", progress => {
      // document.querySelector( "#_dm-dropzoneProgress" ).style.opacity = "0";
   });


   // Setup the buttons for all transfers
   uplodaBtn.addEventListener( "click", () => {
      //Upload all files
      //customStyleDz.enqueueFiles(customStyleDz.getFilesWithStatus(Dropzone.ADDED));
   });

   removeBtn.addEventListener( "click", () => {
      customStyleDz.removeAllFiles(true);
      uplodaBtn.setAttribute( "disabled", true);
      removeBtn.setAttribute( "disabled", true);
   });

})
