document.addEventListener( "DOMContentLoaded", () => {

   // Quill Texteditor
   // ---------------------------------------------------------------------------------
   new Quill("#_dm-textEditor", {
      modules: {
         toolbar: [

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
         ]
      },
      theme: "snow"
   });

})
