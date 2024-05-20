document.addEventListener( "DOMContentLoaded", () => {


   // Default toolbar
   // ---------------------------------------------------------------------------------
   let defaultQuill = new Quill("#_dm-quillDefault", {
      theme: "snow"   // Specify theme in configuration
   });



   // Simple Toolbar
   // ---------------------------------------------------------------------------------
   const quillToolbarOptions = [

      [{ "font": [] }],
      [{ "header": [1, 2, 3, 4, 5, 6, false] }],

      ["bold", "italic", "underline", "strike"],              // toggled buttons
      [{ "color": [] }, { "background": [] }],                // dropdown with defaults from theme

      [{ "list": "ordered" }, { "list": "bullet" }],
      [{ "align": [] }],

      // More options
      // ---------------------------------------------------------------------------------
      // [{ "size": ["small", false, "large", "huge"] }],     // custom dropdown
      // [{ "header": 1 }, { "header": 2 }],                  // custom button values
      // [{ "script": "sub" }, { "script": "super" }],        // superscript/subscript
      // [{ "indent": "-1" }, { "indent": "+1" }],            // outdent/indent
      // [{ "direction": "rtl" }],                            // text direction
      // ["blockquote", "code-block"],
      // ["clean"]                                            // remove formatting button
   ];


   const customToolbarQuill = new Quill("#_dm-quillCustomToolbar", {
      modules: {
         toolbar: quillToolbarOptions
      },
      theme: "snow"
   });



   // Advanced toolbar
   // All toolbar components are described in HTML.
   // ---------------------------------------------------------------------------------
   const advancedToolbarQuill = new Quill("#_dm-quillAdvancedEditor", {
      modules: {
         toolbar: { container: "#_dm-quillAdvancedToolbar" }
      },
      theme: "snow"
   });



   // Popup bar / Bubble
   // ---------------------------------------------------------------------------------
   const bubbleQuill = new Quill("#_dm-quillBubble", {
      theme: "bubble"
   });


});
