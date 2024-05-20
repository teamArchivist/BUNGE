document.addEventListener( "DOMContentLoaded", () => {

   // Quill Texteditor with advanced toolbar
   // All toolbar components are described in HTML.
   // ---------------------------------------------------------------------------------
   const advancedToolbarQuill = new Quill("#_dm-quillAdvancedEditor", {
      modules: {
         toolbar: { container: "#_dm-quillAdvancedToolbar" }
      },
      theme: "snow"
   });

})
