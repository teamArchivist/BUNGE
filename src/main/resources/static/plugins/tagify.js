

// BASIC EXAMPLE
// ---------------------------------------------------------------------------------
// initialize Tagify on the above input node reference
new Tagify(document.querySelector('input[name=basic]'));





// TEXT INPUT
// ---------------------------------------------------------------------------------
function tagifyTextInput() {
   const tagify_text_input = document.querySelector('input[name=input]');
   const whitelist = ["A# .NET", "A# (Axiom)", "A-0 System", "A+", "A++", "ABAP", "ABC", "ABC ALGOL", "ABSET", "ABSYS", "ACC", "Accent", "Ace DASL", "ACL2", "Avicsoft", "ACT-III", "Action!", "ActionScript", "Ada", "Adenine", "Agda", "Agilent VEE", "Agora", "AIMMS", "Alef", "ALF", "ALGOL 58", "ALGOL 60", "ALGOL 68", "ALGOL W", "Alice", "Alma-0", "AmbientTalk", "Amiga E", "AMOS", "AMPL", "Apex (Salesforce.com)", "APL", "AppleScript", "Arc", "ARexx", "Argus", "AspectJ", "Assembly language", "ATS", "Ateji PX", "AutoHotkey", "Autocoder", "AutoIt", "AutoLISP / Visual LISP", "Averest", "AWK", "Axum", "Active Server Pages", "ASP.NET", "B", "Babbage", "Bash", "BASIC", "bc", "BCPL", "BeanShell", "Batch (Windows/Dos)", "Bertrand", "BETA", "Bigwig", "Bistro", "BitC", "BLISS", "Blockly", "BlooP", "Blue", "Boo", "Boomerang", "Bourne shell (including bash and ksh)", "BREW", "BPEL", "B", "C--", "C++ – ISO/IEC 14882", "C# – ISO/IEC 23270", "C/AL", "Caché ObjectScript", "C Shell", "Caml", "Cayenne", "CDuce", "Cecil", "Cesil", "Céu", "Ceylon", "CFEngine", "CFML", "Cg", "Ch", "Chapel", "Charity", "Charm", "Chef", "CHILL", "CHIP-8", "chomski", "ChucK", "CICS", "Cilk", "Citrine (programming language)", "CL (IBM)", "Claire", "Clarion", "Clean", "Clipper", "CLIPS", "CLIST", "Clojure", "CLU", "CMS-2", "COBOL – ISO/IEC 1989", "CobolScript – COBOL Scripting language", "Cobra", "CODE", "CoffeeScript", "ColdFusion", "COMAL", "Combined Programming Language (CPL)", "COMIT", "Common Intermediate Language (CIL)", "Common Lisp (also known as CL)", "COMPASS", "Component Pascal", "Constraint Handling Rules (CHR)", "COMTRAN", "Converge", "Cool", "Coq", "Coral 66", "Corn", "CorVision", "COWSEL", "CPL", "CPL", "Cryptol", "csh", "Csound", "CSP", "CUDA", "Curl", "Curry", "Cybil", "Cyclone", "Cython", "Java", "Javascript", "M2001", "M4", "M#", "Machine code", "MAD (Michigan Algorithm Decoder)", "MAD/I", "Magik", "Magma", "make", "Maple", "MAPPER now part of BIS", "MARK-IV now VISION:BUILDER", "Mary", "MASM Microsoft Assembly x86", "MATH-MATIC", "Mathematica", "MATLAB", "Maxima (see also Macsyma)", "Max (Max Msp – Graphical Programming Environment)", "Maya (MEL)", "MDL", "Mercury", "Mesa", "Metafont", "Microcode", "MicroScript", "MIIS", "Milk (programming language)", "MIMIC", "Mirah", "Miranda", "MIVA Script", "ML", "Model 204", "Modelica", "Modula", "Modula-2", "Modula-3", "Mohol", "MOO", "Mortran", "Mouse", "MPD", "Mathcad", "MSIL – deprecated name for CIL", "MSL", "MUMPS", "Mystic Programming L"];


   // initialize Tagify on the above input node reference
   const tagify_ti = new Tagify(tagify_text_input, {
      enforceWhitelist: true,
      whitelist: tagify_text_input.value.trim().split(/\s*,\s*/) // Array of values. stackoverflow.com/a/43375571/104380
   });


   // "remove all tags" button event listener
   document.querySelector('.tags--removeAllBtn').addEventListener('click', tagify_ti.removeAllTags.bind(tagify_ti))


   // Chainable event listeners
   tagify_ti.on('add', onAddTag)
      .on('remove', onRemoveTag)
      .on('input', onInput)
      .on('edit', onTagEdit)
      .on('invalid', onInvalidTag)
      .on('click', onTagClick)
      .on('focus', onTagifyFocusBlur)
      .on('blur', onTagifyFocusBlur)
      .on('dropdown:hide dropdown:show', e => console.log(e.type))
      .on('dropdown:select', onDropdownSelect)


   let tagify_ti_mockAjax = (function tagify_ti_mockAjax() {
      var timeout;
      return function(duration) {
         clearTimeout(timeout); // abort last request
         return new Promise(function(resolve, reject) {
            timeout = setTimeout(resolve, duration || 700, whitelist)
         })
      }
   })()


   // tag added callback
   function onAddTag(e) {
      console.log("onAddTag: ", e.detail);
      console.log("original input value: ", tagify_text_input.value)
      tagify_ti.off('add', onAddTag) // exmaple of removing a custom Tagify event
   }


   // tag remvoed callback
   function onRemoveTag(e) {
      console.log("onRemoveTag:", e.detail, "tagify instance value:", tagify_ti.value)
   }


   // on character(s) added/removed (user is typing/deleting)
   function onInput(e) {
      console.log("onInput: ", e.detail);
      tagify_ti.whitelist = null; // reset current whitelist
      tagify_ti.loading(true) // show the loader animation

      // get new whitelist from a delayed mocked request (Promise)
      tagify_ti_mockAjax()
         .then(function(result) {
            tagify_ti.settings.whitelist = result.concat(tagify_ti.value) // add already-existing tags to the new whitelist array

            tagify_ti
               .loading(false)
               // render the suggestions dropdown.
               .dropdown.show(e.detail.value);
         })
         .catch(err => tagify_ti.dropdown.hide())
   }


   function onTagEdit(e) {
      console.log("onTagEdit: ", e.detail);
   }


   // invalid tag added callback
   function onInvalidTag(e) {
      console.log("onInvalidTag: ", e.detail);
   }


   // invalid tag added callback
   function onTagClick(e) {
      console.log(e.detail);
      console.log("onTagClick: ", e.detail);
   }


   function onTagifyFocusBlur(e) {
      console.log(e.type, "event fired")
   }


   function onDropdownSelect(e) {
      console.log("onDropdownSelect: ", e.detail)
   }
}
tagifyTextInput();





// CUSTOM SUGGESTIONS
// ---------------------------------------------------------------------------------
const tagify_costum_suggestion = document.querySelector('input[name="input-custom-dropdown"]');

// Init Tagify script on the above inputs
const tagify_cs = new Tagify( tagify_costum_suggestion, {
   whitelist: ["A# .NET", "A# (Axiom)", "A-0 System", "A+", "A++", "ABAP", "ABC", "ABC ALGOL", "ABSET", "ABSYS", "ACC", "Accent", "Ace DASL", "ACL2", "Avicsoft", "ACT-III", "Action!", "ActionScript", "Ada", "Adenine", "Agda", "Agilent VEE", "Agora", "AIMMS", "Alef", "ALF", "ALGOL 58", "ALGOL 60", "ALGOL 68", "ALGOL W", "Alice", "Alma-0", "AmbientTalk", "Amiga E", "AMOS", "AMPL", "Apex (Salesforce.com)", "APL", "AppleScript", "Arc", "ARexx", "Argus", "AspectJ", "Assembly language", "ATS", "Ateji PX", "AutoHotkey", "Autocoder", "AutoIt", "AutoLISP / Visual LISP", "Averest", "AWK", "Axum", "Active Server Pages", "ASP.NET", "B", "Babbage", "Bash", "BASIC", "bc", "BCPL", "BeanShell", "Batch (Windows/Dos)", "Bertrand", "BETA", "Bigwig", "Bistro", "BitC", "BLISS", "Blockly", "BlooP", "Blue", "Boo", "Boomerang", "Bourne shell (including bash and ksh)", "BREW", "BPEL", "B", "C--", "C++ – ISO/IEC 14882", "C# – ISO/IEC 23270", "C/AL", "Caché ObjectScript", "C Shell", "Caml", "Cayenne", "CDuce", "Cecil", "Cesil", "Céu", "Ceylon", "CFEngine", "CFML", "Cg", "Ch", "Chapel", "Charity", "Charm", "Chef", "CHILL", "CHIP-8", "chomski", "ChucK", "CICS", "Cilk", "Citrine (programming language)", "CL (IBM)", "Claire", "Clarion", "Clean", "Clipper", "CLIPS", "CLIST", "Clojure", "CLU", "CMS-2", "COBOL – ISO/IEC 1989", "CobolScript – COBOL Scripting language", "Cobra", "CODE", "CoffeeScript", "ColdFusion", "COMAL", "Combined Programming Language (CPL)", "COMIT", "Common Intermediate Language (CIL)", "Common Lisp (also known as CL)", "COMPASS", "Component Pascal", "Constraint Handling Rules (CHR)", "COMTRAN", "Converge", "Cool", "Coq", "Coral 66", "Corn", "CorVision", "COWSEL", "CPL", "CPL", "Cryptol", "csh", "Csound", "CSP", "CUDA", "Curl", "Curry", "Cybil", "Cyclone", "Cython", "Java", "Javascript", "M2001", "M4", "M#", "Machine code", "MAD (Michigan Algorithm Decoder)", "MAD/I", "Magik", "Magma", "make", "Maple", "MAPPER now part of BIS", "MARK-IV now VISION:BUILDER", "Mary", "MASM Microsoft Assembly x86", "MATH-MATIC", "Mathematica", "MATLAB", "Maxima (see also Macsyma)", "Max (Max Msp – Graphical Programming Environment)", "Maya (MEL)", "MDL", "Mercury", "Mesa", "Metafont", "Microcode", "MicroScript", "MIIS", "Milk (programming language)", "MIMIC", "Mirah", "Miranda", "MIVA Script", "ML", "Model 204", "Modelica", "Modula", "Modula-2", "Modula-3", "Mohol", "MOO", "Mortran", "Mouse", "MPD", "Mathcad", "MSIL – deprecated name for CIL", "MSL", "MUMPS", "Mystic Programming L"],
   maxTags: 10,
   dropdown: {
      maxItems: 50, // <- mixumum allowed rendered suggestions
      classname: "tags-look", // <- custom classname for this dropdown, so it could be targeted
      enabled: 0, // <- show suggestions on focus
      closeOnSelect: false // <- do not hide the suggestions dropdown once an item has been selected
   }
})





// TEXTAREA
// ---------------------------------------------------------------------------------
const tagify_textarea = document.querySelector('textarea[name=tags2]');
const tagify_ta = new Tagify(tagify_textarea, {
   enforceWhitelist: true,
   delimiters: null,
   whitelist: ["The Shawshank Redemption", "The Godfather", "The Godfather: Part II", "The Dark Knight", "12 Angry Men", "Schindler's List", "Pulp Fiction", "The Lord of the Rings: The Return of the King", "The Good, the Bad and the Ugly", "Fight Club", "The Lord of the Rings: The Fellowship of the Ring", "Star Wars: Episode V - The Empire Strikes Back", "Forrest Gump", "Inception", "The Lord of the Rings: The Two Towers", "One Flew Over the Cuckoo's Nest", "Goodfellas", "The Matrix", "Seven Samurai", "Star Wars: Episode IV - A New Hope", "City of God", "Se7en", "The Silence of the Lambs", "It's a Wonderful Life", "The Usual Suspects", "Life Is Beautiful", "Léon: The Professional", "Spirited Away", "Saving Private Ryan", "La La Land", "Once Upon a Time in the West", "American History X", "Interstellar", "Casablanca", "Psycho", "City Lights", "The Green Mile", "Raiders of the Lost Ark", "The Intouchables", "Modern Times", "Rear Window", "The Pianist", "The Departed", "Terminator 2: Judgment Day", "Back to the Future", "Whiplash", "Gladiator", "Memento", "Apocalypse Now", "The Prestige", "The Lion King", "Alien", "Dr. Strangelove or: How I Learned to Stop Worrying and Love the Bomb", "Sunset Boulevard", "The Great Dictator", "Cinema Paradiso", "The Lives of Others", "Paths of Glory", "Grave of the Fireflies", "Django Unchained", "The Shining", "WALL·E", "American Beauty", "The Dark Knight Rises", "Princess Mononoke", "Aliens", "Oldboy", "Once Upon a Time in America", "Citizen Kane", "Das Boot", "Witness for the Prosecution", "North by Northwest", "Vertigo", "Star Wars: Episode VI - Return of the Jedi", "Reservoir Dogs", "M", "Braveheart", "Amélie", "Requiem for a Dream", "A Clockwork Orange", "Taxi Driver", "Lawrence of Arabia", "Like Stars on Earth", "Double Indemnity", "To Kill a Mockingbird", "Eternal Sunshine of the Spotless Mind", "Toy Story 3", "Amadeus", "My Father and My Son", "Full Metal Jacket", "The Sting", "2001: A Space Odyssey", "Singin' in the Rain", "Bicycle Thieves", "Toy Story", "Dangal", "The Kid", "Inglourious Basterds", "Snatch", "Monty Python and the Holy Grail", "Hacksaw Ridge", "3 Idiots", "L.A. Confidential", "For a Few Dollars More", "Scarface", "Rashomon", "The Apartment", "The Hunt", "Good Will Hunting", "Indiana Jones and the Last Crusade", "A Separation", "Metropolis", "Yojimbo", "All About Eve", "Batman Begins", "Up", "Some Like It Hot", "The Treasure of the Sierra Madre", "Unforgiven", "Downfall", "Raging Bull", "The Third Man", "Die Hard", "Children of Heaven", "The Great Escape", "Heat", "Chinatown", "Inside Out", "Pan's Labyrinth", "Ikiru", "My Neighbor Totoro", "On the Waterfront", "Room", "Ran", "The Gold Rush", "The Secret in Their Eyes", "The Bridge on the River Kwai", "Blade Runner", "Mr. Smith Goes to Washington", "The Seventh Seal", "Howl's Moving Castle", "Lock, Stock and Two Smoking Barrels", "Judgment at Nuremberg", "Casino", "The Bandit", "Incendies", "A Beautiful Mind", "A Wednesday", "The General", "The Elephant Man", "Wild Strawberries", "Arrival", "V for Vendetta", "Warrior", "The Wolf of Wall Street", "Manchester by the Sea", "Sunrise", "The Passion of Joan of Arc", "Gran Torino", "Rang De Basanti", "Trainspotting", "Dial M for Murder", "The Big Lebowski", "The Deer Hunter", "Tokyo Story", "Gone with the Wind", "Fargo", "Finding Nemo", "The Sixth Sense", "The Thing", "Hera Pheri", "Cool Hand Luke", "Andaz Apna Apna", "Rebecca", "No Country for Old Men", "How to Train Your Dragon", "Munna Bhai M.B.B.S.", "Sholay", "Kill Bill: Vol. 1", "Into the Wild", "Mary and Max", "Gone Girl", "There Will Be Blood", "Come and See", "It Happened One Night", "Life of Brian", "Rush", "Hotel Rwanda", "Platoon", "Shutter Island", "Network", "The Wages of Fear", "Stand by Me", "Wild Tales", "In the Name of the Father", "Spotlight", "Star Wars: The Force Awakens", "The Nights of Cabiria", "The 400 Blows", "Butch Cassidy and the Sundance Kid", "Mad Max: Fury Road", "The Maltese Falcon", "12 Years a Slave", "Ben-Hur", "The Grand Budapest Hotel", "Persona", "Million Dollar Baby", "Amores Perros", "Jurassic Park", "The Princess Bride", "Hachi: A Dog's Tale", "Memories of Murder", "Stalker", "Nausicaä of the Valley of the Wind", "Drishyam", "The Truman Show", "The Grapes of Wrath", "Before Sunrise", "Touch of Evil", "Annie Hall", "The Message", "Rocky", "Gandhi", "Harry Potter and the Deathly Hallows: Part 2", "The Bourne Ultimatum", "Diabolique", "Donnie Darko", "Monsters, Inc.", "Prisoners", "8½", "The Terminator", "The Wizard of Oz", "Catch Me If You Can", "Groundhog Day", "Twelve Monkeys", "Zootopia", "La Haine", "Barry Lyndon", "Jaws", "The Best Years of Our Lives", "Infernal Affairs", "Udaan", "The Battle of Algiers", "Strangers on a Train", "Dog Day Afternoon", "Sin City", "Kind Hearts and Coronets", "Gangs of Wasseypur", "The Help"],
   callbacks: {
      add: console.log, // callback when adding a tag
      remove: console.log // callback when removing a tag
   }
});





// EASY COSTUMIZE
// ---------------------------------------------------------------------------------
function easyCutomize(){

   // generate random whilist items (for the demo)
   const randomStringsArr = Array.apply(null, Array(100)).map(function() {
      return Array.apply(null, Array(~~(Math.random() * 10 + 3))).map(function() {
         return String.fromCharCode(Math.random() * (123 - 97) + 97)
      }).join('') + '@gmail.com'
   })

   const tagify_easy_customize = document.querySelector('.customLook');
   const button = tagify_easy_customize.nextElementSibling; // "add new tag" action-button
   const tagify_ec = new Tagify(tagify_easy_customize, {

      // email address validation (https://stackoverflow.com/a/46181/104380)
      pattern: /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
      whitelist: randomStringsArr,
      callbacks: {
         "invalid": onInvalidTag
      },
      dropdown: {
         position: 'text',
         enabled: 1 // show suggestions dropdown after 1 typed character
      }
   })

   button.addEventListener("click", onAddButtonClick)

   function onAddButtonClick() {
      tagify_ec.addEmptyTag()
   }

   function onInvalidTag(e) {
      console.log("invalid", e.detail)
   }
}
easyCutomize();






// MIX TEXT AND TAG
// ---------------------------------------------------------------------------------
function mixTextAndTag(){
   // Define two types of whitelists, each used for the dropdown suggestions menu,
   // depending on the prefix pattern typed (@/#). See settings below.
   const whitelist_1 = [
      { value: 100, text: 'kenny', title: 'Kenny McCormick' },
      { value: 200, text: 'cartman', title: 'Eric Cartman' },
      { value: 300, text: 'kyle', title: 'Kyle Broflovski' },
      { value: 400, text: 'token', title: 'Token Black' },
      { value: 500, text: 'jimmy', title: 'Jimmy Valmer' },
      { value: 600, text: 'butters', title: 'Butters Stotch' },
      { value: 700, text: 'stan', title: 'Stan Marsh' },
      { value: 800, text: 'randy', title: 'Randy Marsh' },
      { value: 900, text: 'Mr. Garrison', title: 'POTUS' },
      { value: 1000, text: 'Mr. Mackey', title: "M'Kay" }
   ]

   // Second whitelist, which is shown only when starting to type "#".
   // Thiw whitelist is the most simple one possible.
   const whitelist_2 = ['Homer simpson', 'Marge simpson', 'Bart', 'Lisa', 'Maggie', 'Mr. Burns', 'Ned', 'Milhouse', 'Moe'];


   // Initialize Tagify
   const tagify_mix_tag = document.querySelector('[name=mix]');

   // init Tagify script on the above inputs
   const tagify_mt = new Tagify(tagify_mix_tag, {

      //  mixTagsInterpolator: ["{{", "}}"],
      mode: 'mix',  // <--  Enable mixed-content
      pattern: /@|#/,  // <--  Text starting with @ or # (if single, String can be used here)
      tagTextProp: 'text',  // <-- the default property (from whitelist item) for the text to be rendered in a tag element.
      // Array for initial interpolation, which allows only these tags to be used
      whitelist: whitelist_1.concat(whitelist_2).map(function(item){
         return typeof item == 'string' ? {value:item} : item
      }),

      dropdown : {
         enabled: 1,
         position: 'text', // <-- render the suggestions list next to the typed text ("caret")
         mapValueTo: 'text', // <-- similar to above "tagTextProp" setting, but for the dropdown items
         highlightFirst: true  // automatically highlights first sugegstion item in the dropdown
      },

      callbacks: {
         add: console.log,  // callback when adding a tag
         remove: console.log   // callback when removing a tag
      }
   })

   // A good place to pull server suggestion list accoring to the prefix/value
   tagify_mt.on('input', function(e){
      const prefix = e.detail.prefix;

      // first, clean the whitlist array, because the below code, while not, might be async,
      // therefore it should be up to you to decide WHEN to render the suggestions dropdown
      // tagify_mt.settings.whitelist.length = 0;

      if( prefix ){
         if( prefix == '@' )
               tagify_mt.whitelist = whitelist_1;

         if( prefix == '#' )
               tagify_mt.whitelist = whitelist_2;

         if( e.detail.value.length > 1 )
               tagify_mt.dropdown.show(e.detail.value);
      }

      console.log( tagify_mt.value )
      console.log('mix-mode "input" event value: ', e.detail)
   })

   tagify_mt.on('add', function(e){
      console.log(e)
   })
}
mixTextAndTag();





// OUTSIDE-OF-THE-BOX
// ---------------------------------------------------------------------------------
const tagify_outside_box = document.querySelector('input[name=tags-outside]')

// init Tagify script on the above inputs
const tagify_ob = new Tagify(tagify_outside_box, {
   whitelist: ["foo", "bar", "baz", "acc", "bat", "cat", "dat", "efg"],
   dropdown: {
      position: "input",
      enabled: 0 // always opens dropdown when input gets focus
   }
})





// RENDER SUGGESTIONS LIST MANUALLY
// ---------------------------------------------------------------------------------
function renderSuggestionListManually() {

   // Render Suggestions List Manually
   const tagify_render_suggestion = document.querySelector('input[name=tags-manual-suggestions]');

   // init Tagify script on the above inputs
   const tagify_rs = new Tagify(tagify_render_suggestion, {
      whitelist: ["A# .NET", "A# (Axiom)", "A-0 System", "A+", "A++", "ABAP", "ABC", "ABC ALGOL", "ABSET", "ABSYS", "ACC", "Accent", "Ace DASL", "ACL2", "Avicsoft", "ACT-III", "Action!", "ActionScript", "Ada", "Adenine", "Agda", "Agilent VEE", "Agora", "AIMMS", "Alef", "ALF", "ALGOL 58", "ALGOL 60", "ALGOL 68", "ALGOL W", "Alice", "Alma-0", "AmbientTalk", "Amiga E", "AMOS", "AMPL", "Apex (Salesforce.com)", "APL", "AppleScript", "Arc", "ARexx", "Argus", "AspectJ", "Assembly language", "ATS", "Ateji PX", "AutoHotkey", "Autocoder", "AutoIt", "AutoLISP / Visual LISP", "Averest", "AWK", "Axum", "Active Server Pages", "ASP.NET", "B", "Babbage", "Bash", "BASIC", "bc", "BCPL", "BeanShell", "Batch (Windows/Dos)", "Bertrand", "BETA", "Bigwig", "Bistro", "BitC", "BLISS", "Blockly", "BlooP", "Blue", "Boo", "Boomerang", "Bourne shell (including bash and ksh)", "BREW", "BPEL", "B", "C--", "C++ – ISO/IEC 14882", "C# – ISO/IEC 23270", "C/AL", "Caché ObjectScript", "C Shell", "Caml", "Cayenne", "CDuce", "Cecil", "Cesil", "Céu", "Ceylon", "CFEngine", "CFML", "Cg", "Ch", "Chapel", "Charity", "Charm", "Chef", "CHILL", "CHIP-8", "chomski", "ChucK", "CICS", "Cilk", "Citrine (programming language)", "CL (IBM)", "Claire", "Clarion", "Clean", "Clipper", "CLIPS", "CLIST", "Clojure", "CLU", "CMS-2", "COBOL – ISO/IEC 1989", "CobolScript – COBOL Scripting language", "Cobra", "CODE", "CoffeeScript", "ColdFusion", "COMAL", "Combined Programming Language (CPL)", "COMIT", "Common Intermediate Language (CIL)", "Common Lisp (also known as CL)", "COMPASS", "Component Pascal", "Constraint Handling Rules (CHR)", "COMTRAN", "Converge", "Cool", "Coq", "Coral 66", "Corn", "CorVision", "COWSEL", "CPL", "CPL", "Cryptol", "csh", "Csound", "CSP", "CUDA", "Curl", "Curry", "Cybil", "Cyclone", "Cython", "Java", "Javascript", "M2001", "M4", "M#", "Machine code", "MAD (Michigan Algorithm Decoder)", "MAD/I", "Magik", "Magma", "make", "Maple", "MAPPER now part of BIS", "MARK-IV now VISION:BUILDER", "Mary", "MASM Microsoft Assembly x86", "MATH-MATIC", "Mathematica", "MATLAB", "Maxima (see also Macsyma)", "Max (Max Msp – Graphical Programming Environment)", "Maya (MEL)", "MDL", "Mercury", "Mesa", "Metafont", "Microcode", "MicroScript", "MIIS", "Milk (programming language)", "MIMIC", "Mirah", "Miranda", "MIVA Script", "ML", "Model 204", "Modelica", "Modula", "Modula-2", "Modula-3", "Mohol", "MOO", "Mortran", "Mouse", "MPD", "Mathcad", "MSIL – deprecated name for CIL", "MSL", "MUMPS", "Mystic Programming L"],
      dropdown: {
         position: "manual",
         maxItems: Infinity,
         enabled: 0,
         classname: "customSuggestionsList"
      },
      templates: {
         dropdownItemNoMatch() {
            return `<div class='empty'>Nothing Found</div>`;
         }
      },
      enforceWhitelist: true
   })

   tagify_rs.on("dropdown:show", onSuggestionsListUpdate)
      .on("dropdown:hide", onSuggestionsListHide)
      .on('dropdown:scroll', onDropdownScroll)

   renderSuggestionsList()  // defined down below

   // ES2015 argument destructuring
   function onSuggestionsListUpdate({ detail: suggestionsElm }) {
      console.log(suggestionsElm)
   }

   function onSuggestionsListHide() {
      console.log("hide dropdown")
   }

   function onDropdownScroll(e) {
      console.log(e.detail)
   }

   // https://developer.mozilla.org/en-US/docs/Web/API/Element/insertAdjacentElement
   function renderSuggestionsList() {
      tagify_rs.dropdown.show() // load the list
      tagify_rs.DOM.scope.parentNode.appendChild(tagify_rs.DOM.dropdown)
   }
}
renderSuggestionListManually();





// DISABLED USER INPUT
// ---------------------------------------------------------------------------------
const tagify_disabled_user_input = document.querySelector('input[name=tags-disabled-user-input]');
new Tagify(tagify_disabled_user_input, {
   whitelist: [1,2,3,4,5],
   userInput: false
})





// USERS LIST
// ---------------------------------------------------------------------------------
function userList(){

   // https://www.mockaroo.com/
   const tagify_user_list = document.querySelector('input[name=users-list-tags]');

   function tagTemplate(tagData) {
      return `
         <tag title="${tagData.email}"
            contenteditable='false'
            spellcheck='false'
            tabIndex="-1"
            class="tagify__tag ${tagData.class ? tagData.class : ""}"
            ${this.getAttributes(tagData)}>
            <x title='' class='tagify__tag__removeBtn' role='button' aria-label='remove tag'></x>
            <div>
               <div class='tagify__tag__avatar-wrap'>
                  <img onerror="this.style.visibility='hidden'" src="${tagData.avatar}">
               </div>
               <span class='tagify__tag-text'>${tagData.name}</span>
            </div>
         </tag>
      `
   }


   function suggestionItemTemplate(tagData){
      return `
         <div ${this.getAttributes(tagData)}
            class='tagify__dropdown__item ${tagData.class ? tagData.class : ""}'
            tabindex="0"
            role="option">
            ${ tagData.avatar ? `
               <div class='tagify__dropdown__item__avatar-wrap'>
                  <img onerror="this.style.visibility='hidden'" src="${tagData.avatar}">
               </div>` : ''
            }
            <strong>${tagData.name}</strong>
            <span>${tagData.email}</span>
         </div>
      `
   }


   function dropdownHeaderTemplate(suggestions){
      return `
         <header data-selector='tagify-suggestions-header' class="${this.settings.classNames.dropdownItem} ${this.settings.classNames.dropdownItem}__addAll">
            <div>
               <strong>${this.value.length ? `Add Remaning` : 'Add All'}</strong>
               <a class='remove-all-tags'>Remove all</a>
            </div>
            <span>${suggestions.length} members</span>
         </header>
      `
   }

   // Initialize Tagify on the above input node reference
   const tagify_ul = new Tagify(tagify_user_list, {
      tagTextProp: 'name', // very important since a custom template is used with this property as text
      // enforceWhitelist: true,
      skipInvalid: true, // do not remporarily add invalid tags
      dropdown: {
         closeOnSelect: false,
         enabled: 0,
         classname: 'users-list',
         searchKeys: ['name', 'email']  // very important to set by which keys to search for suggesttions when typing
      },
      templates: {
         tag: tagTemplate,
         dropdownItem: suggestionItemTemplate,
         dropdownHeader: dropdownHeaderTemplate
      },
      whitelist: [
         {
               "value": 1,
               "name": "Justinian Hattersley",
               "avatar": "https://i.pravatar.cc/80?img=1",
               "email": "jhattersley0@ucsd.edu",
               "team": "A"
         },
         {
               "value": 2,
               "name": "Antons Esson",
               "avatar": "https://i.pravatar.cc/80?img=2",
               "email": "aesson1@ning.com",
               "team": "B"

         },
         {
               "value": 3,
               "name": "Ardeen Batisse",
               "avatar": "https://i.pravatar.cc/80?img=3",
               "email": "abatisse2@nih.gov",
               "team": "A"
         },
         {
               "value": 4,
               "name": "Graeme Yellowley",
               "avatar": "https://i.pravatar.cc/80?img=4",
               "email": "gyellowley3@behance.net",
               "team": "C"
         },
         {
               "value": 5,
               "name": "Dido Wilford",
               "avatar": "https://i.pravatar.cc/80?img=5",
               "email": "dwilford4@jugem.jp",
               "team": "A"
         },
         {
               "value": 6,
               "name": "Celesta Orwin",
               "avatar": "https://i.pravatar.cc/80?img=6",
               "email": "corwin5@meetup.com",
               "team": "C"
         },
         {
               "value": 7,
               "name": "Sally Main",
               "avatar": "https://i.pravatar.cc/80?img=7",
               "email": "smain6@techcrunch.com",
               "team": "A"
         },
         {
               "value": 8,
               "name": "Grethel Haysman",
               "avatar": "https://i.pravatar.cc/80?img=8",
               "email": "ghaysman7@mashable.com",
               "team": "B"
         },
         {
               "value": 9,
               "name": "Marvin Mandrake",
               "avatar": "https://i.pravatar.cc/80?img=9",
               "email": "mmandrake8@sourceforge.net",
               "team": "B"
         },
         {
               "value": 10,
               "name": "Corrie Tidey",
               "avatar": "https://i.pravatar.cc/80?img=10",
               "email": "ctidey9@youtube.com",
               "team": "A"
         },
         {
               "value": 11,
               "name": "foo",
               "avatar": "https://i.pravatar.cc/80?img=11",
               "email": "foo@bar.com",
               "team": "B"
         },
         {
               "value": 12,
               "name": "foo",
               "avatar": "https://i.pravatar.cc/80?img=12",
               "email": "foo.aaa@foo.com",
               "team": "A"
         },
      ],

      transformTag: (tagData, originalData) => {
         var {name, email} = parseFullValue(tagData.name)
         tagData.name = name
         tagData.email = email || tagData.email
      },

      validate({name, email}) {
         // when editing a tag, there will only be the "name" property which contains name + email (see 'transformTag' above)
         if( !email && name ) {
               var parsed = parseFullValue(name)
               name = parsed.name
               email = parsed.email
         }

         if( !name ) return "Missing name"
         if( !validateEmail(email) ) return "Invalid email"

         return true
      }
   })

   // The below code is printed as escaped, so please copy this function from:
   // https://github.com/yairEO/tagify/blob/master/src/parts/helpers.js#L89-L97
   function escapeHTML( s ){
      return typeof s == 'string' ? s
         .replace(/&/g, "&")
         .replace(/</g, "<")
         .replace(/>/g, ">")
         .replace(/"/g, "'")
         .replace(/`|'/g, "'")
         : s;
   }

   // The below part is only if you want to split the users into groups, when rendering the suggestions list dropdown:
   // (since each user also has a 'team' property)
   tagify_ul.dropdown.createListHTML = sugegstionsList  => {
      const teamsOfUsers = sugegstionsList.reduce((acc, suggestion) => {
         const team = suggestion.team || 'Not Assigned';

         if( !acc[team] )
               acc[team] = [suggestion]
         else
               acc[team].push(suggestion)

         return acc
      }, {});

      const getUsersSuggestionsHTML = teamUsers => teamUsers.map((suggestion, idx) => {
         if( typeof suggestion == 'string' || typeof suggestion == 'number' )
               suggestion = {value:suggestion}

         var value = tagify_ul.dropdown.getMappedValue.call(tagify_ul, suggestion)

         suggestion.value = value && typeof value == 'string' ? escapeHTML(value) : value

         return tagify_ul.settings.templates.dropdownItem.apply(tagify_ul, [suggestion]);
      }).join("")


      // assign the user to a group
      return Object.entries(teamsOfUsers).map(([team, teamUsers]) => {
         return `<div class="tagify__dropdown__itemsGroup" data-title="Team ${team}:">${getUsersSuggestionsHTML(teamUsers)}</div>`
      }).join("")
   }

   // attach events listeners
   tagify_ul.on('dropdown:select', onSelectSuggestion) // allows selecting all the suggested (whitelist) items
         .on('edit:start', onEditStart)  // show custom text in the tag while in edit-mode

   function onSelectSuggestion(e){
      if( e.detail.event.target.matches('.remove-all-tags')) {
         tagify_ul.removeAllTags()
      }

      // custom class from "dropdownHeaderTemplate"
      else if( e.detail.elm.classList.contains(`${tagify_ul.settings.classNames.dropdownItem}__addAll`) )
         tagify_ul.dropdown.selectAll();
   }

   function onEditStart({detail:{tag, data}}){
      tagify_ul.setTagTextNode(tag, `${data.name} <${data.email}>`)
   }

   // https://stackoverflow.com/a/9204568/104380
   function validateEmail(email) {
      return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
   }

   function parseFullValue(value) {
      // https://stackoverflow.com/a/11592042/104380
      const parts = value.split(/<(.*?)>/g),
         name = parts[0].trim(),
         email = parts[1]?.replace(/<(.*?)>/g, '').trim();

      return {name, email}
   }

}
userList();





// ADVANCE OPTIONS
// ---------------------------------------------------------------------------------
function advanceOptions(){

   // Advance Options
   const tagify_advance_option = document.querySelector('input[name=tags3]');
   const tagify_ao = new Tagify(tagify_advance_option, {
      pattern             : /^.{0,20}$/,  // Validate typed tag(s) by Regex. Here maximum chars length is defined as "20"
      delimiters          : ",| ",        // add new tags when a comma or a space character is entered
      trim                : false,        // if "delimiters" setting is using space as a delimeter, then "trim" should be set to "false"
      keepInvalidTags     : true,         // do not remove invalid tags (but keep them marked as invalid)

      // createInvalidTags: false,
      editTags            : {
         clicks: 2,              // single click to edit a tag
         keepInvalid: false      // if after editing, tag is invalid, auto-revert
      },
      maxTags             : 6,
      blacklist           : ["foo", "bar", "baz"],
      whitelist           : ["temple","stun","detective","sign","passion","routine","deck","discriminate","relaxation","fraud","attractive","soft","forecast","point","thank","stage","eliminate","effective","flood","passive","skilled","separation","contact","compromise","reality","district","nationalist","leg","porter","conviction","worker","vegetable","commerce","conception","particle","honor","stick","tail","pumpkin","core","mouse","egg","population","unique","behavior","onion","disaster","cute","pipe","sock","dialect","horse","swear","owner","cope","global","improvement","artist","shed","constant","bond","brink","shower","spot","inject","bowel","homosexual","trust","exclude","tough","sickness","prevalence","sister","resolution","cattle","cultural","innocent","burial","bundle","thaw","respectable","thirsty","exposure","team","creed","facade","calendar","filter","utter","dominate","predator","discover","theorist","hospitality","damage","woman","rub","crop","unpleasant","halt","inch","birthday","lack","throne","maximum","pause","digress","fossil","policy","instrument","trunk","frame","measure","hall","support","convenience","house","partnership","inspector","looting","ranch","asset","rally","explicit","leak","monarch","ethics","applied","aviation","dentist","great","ethnic","sodium","truth","constellation","lease","guide","break","conclusion","button","recording","horizon","council","paradox","bride","weigh","like","noble","transition","accumulation","arrow","stitch","academy","glimpse","case","researcher","constitutional","notion","bathroom","revolutionary","soldier","vehicle","betray","gear","pan","quarter","embarrassment","golf","shark","constitution","club","college","duty","eaux","know","collection","burst","fun","animal","expectation","persist","insure","tick","account","initiative","tourist","member","example","plant","river","ratio","view","coast","latest","invite","help","falsify","allocation","degree","feel","resort","means","excuse","injury","pupil","shaft","allow","ton","tube","dress","speaker","double","theater","opposed","holiday","screw","cutting","picture","laborer","conservation","kneel","miracle","brand","nomination","characteristic","referral","carbon","valley","hot","climb","wrestle","motorist","update","loot","mosquito","delivery","eagle","guideline","hurt","feedback","finish","traffic","competence","serve","archive","feeling","hope","seal","ear","oven","vote","ballot","study","negative","declaration","particular","pattern","suburb","intervention","brake","frequency","drink","affair","contemporary","prince","dry","mole","lazy","undermine","radio","legislation","circumstance","bear","left","pony","industry","mastermind","criticism","sheep","failure","chain","depressed","launch","script","green","weave","please","surprise","doctor","revive","banquet","belong","correction","door","image","integrity","intermediate","sense","formal","cane","gloom","toast","pension","exception","prey","random","nose","predict","needle","satisfaction","establish","fit","vigorous","urgency","X-ray","equinox","variety","proclaim","conceive","bulb","vegetarian","available","stake","publicity","strikebreaker","portrait","sink","frog","ruin","studio","match","electron","captain","channel","navy","set","recommend","appoint","liberal","missile","sample","result","poor","efflux","glance","timetable","advertise","personality","aunt","dog"],
      transformTag        : transformTag,
      backspace           : "edit",
      placeholder         : "Type something",
      dropdown : {
         enabled: 1,            // show suggestion after 1 typed character
         fuzzySearch: false,    // match only suggestions that starts with the typed characters
         position: 'text',      // position suggestions list next to typed text
         caseSensitive: true,   // allow adding duplicate items if their case is different
      },
      templates: {
         dropdownItemNoMatch: function(data) {
            return `<div class='${this.settings.classNames.dropdownItem}' value="noMatch" tabindex="0" role="option">
               No suggestion found for: <strong>${data.value}</strong>
            </div>`
         }
      }
   })

   // generate a random color (in HSL format, which I like to use)
   function getRandomColor(){
      function rand(min, max) {
         return min + Math.random() * (max - min);
      }

      var h = rand(1, 360)|0,
         s = rand(40, 70)|0,
         l = rand(65, 72)|0;

      return 'hsl(' + h + ',' + s + '%,' + l + '%)';
   }

   function transformTag( tagData ){
      tagData.color = getRandomColor();
      tagData.style = "--tag-bg:" + tagData.color;

      if( tagData.value.toLowerCase() == 'shit' ) tagData.value = 's✲✲t';
   }

   tagify_ao.on('add', function(e){
      console.log(e.detail)
   })

   tagify_ao.on('invalid', function(e){
      console.log(e, e.detail);
   })

   let clickDebounce;

   tagify_ao.on('click', function(e){
      const {tag:tagElm, data:tagData} = e.detail;

      // a delay is needed to distinguish between regular click and double-click.
      // this allows enough time for a possible double-click, and noly fires if such
      // did not occur.
      clearTimeout(clickDebounce);
      clickDebounce = setTimeout(() => {
         tagData.color = getRandomColor();
         tagData.style = "--tag-bg:" + tagData.color;
         tagify_ao.replaceTag(tagElm, tagData);
      }, 200);
   })

   tagify_ao.on('dblclick', function(e){
      // when souble clicking, do not change the color of the tag
      clearTimeout(clickDebounce);
   })

}
advanceOptions();





// TAGS WITH PROPERTIES
// ---------------------------------------------------------------------------------
function tagsWithProperties(){

   //Tags With Properties
   const tagify_twp = new Tagify(document.querySelector('input[name=tags3-1]'), {
      delimiters : null,
      templates : {
         tag : function(tagData){
               try{
                  return `<tag title='${tagData.value}' contenteditable='false' spellcheck="false" class='tagify__tag ${tagData.class ? tagData.class : ""}' ${this.getAttributes(tagData)}>
                           <x title='remove tag' class='tagify__tag__removeBtn'></x>
                           <div>
                              ${tagData.code ?
                              `<img onerror="this.style.visibility='hidden'" src='https://flagicons.lipis.dev/flags/4x3/${tagData.code.toLowerCase()}.svg'>` : ''
                              }
                              <span class='tagify__tag-text'>${tagData.value}</span>
                           </div>
                     </tag>`
               }
               catch(err){}
         },

         dropdownItem : function(tagData){
               try {
                  return  `<div ${this.getAttributes(tagData)} class='tagify__dropdown__item ${tagData.class ? tagData.class : ""}' >
                              <img onerror="this.style.visibility = 'hidden'"
                                 src='https://flagicons.lipis.dev/flags/4x3/${tagData.code.toLowerCase()}.svg'>
                              <span>${tagData.value}</span>
                           </div>`
               }
               catch(err){ console.error(err)}
         }
      },
      enforceWhitelist : true,
      whitelist : [
         { value:'Afghanistan', code:'AF' },
         { value:'Åland Islands', code:'AX' },
         { value:'Albania', code:'AL' },
         { value:'Algeria', code:'DZ' },
         { value:'American Samoa', code:'AS' },
         { value:'Andorra', code:'AD' },
         { value:'Angola', code:'AO' },
         { value:'Anguilla', code:'AI' },
         { value:'Antarctica', code:'AQ' },
         { value:'Antigua and Barbuda', code:'AG' },
         { value:'Argentina', code:'AR' },
         { value:'Armenia', code:'AM' },
         { value:'Aruba', code:'AW' },
         { value:'Australia', code:'AU', searchBy:'beach, sub-tropical' },
         { value:'Austria', code:'AT' },
         { value:'Azerbaijan', code:'AZ' },
         { value:'Bahamas', code:'BS' },
         { value:'Bahrain', code:'BH' },
         { value:'Bangladesh', code:'BD' },
         { value:'Barbados', code:'BB' },
         { value:'Belarus', code:'BY' },
         { value:'Belgium', code:'BE' },
         { value:'Belize', code:'BZ' },
         { value:'Benin', code:'BJ' },
         { value:'Bermuda', code:'BM' },
         { value:'Bhutan', code:'BT' },
         { value:'Bolivia', code:'BO' },
         { value:'Bosnia and Herzegovina', code:'BA' },
         { value:'Botswana', code:'BW' },
         { value:'Bouvet Island', code:'BV' },
         { value:'Brazil', code:'BR' },
         { value:'British Indian Ocean Territory', code:'IO' },
         { value:'Brunei Darussalam', code:'BN' },
         { value:'Bulgaria', code:'BG' },
         { value:'Burkina Faso', code:'BF' },
         { value:'Burundi', code:'BI' },
         { value:'Cambodia', code:'KH' },
         { value:'Cameroon', code:'CM' },
         { value:'Canada', code:'CA' },
         { value:'Cape Verde', code:'CV' },
         { value:'Cayman Islands', code:'KY' },
         { value:'Central African Republic', code:'CF' },
         { value:'Chad', code:'TD' },
         { value:'Chile', code:'CL' },
         { value:'China', code:'CN' },
         { value:'Christmas Island', code:'CX' },
         { value:'Cocos (Keeling) Islands', code:'CC' },
         { value:'Colombia', code:'CO' },
         { value:'Comoros', code:'KM' },
         { value:'Congo', code:'CG' },
         { value:'Congo, The Democratic Republic of the', code:'CD' },
         { value:'Cook Islands', code:'CK' },
         { value:'Costa Rica', code:'CR' },
         { value:'Cote D\'Ivoire', code:'CI' },
         { value:'Croatia', code:'HR' },
         { value:'Cuba', code:'CU' },
         { value:'Cyprus', code:'CY' },
         { value:'Czech Republic', code:'CZ' },
         { value:'Denmark', code:'DK' },
         { value:'Djibouti', code:'DJ' },
         { value:'Dominica', code:'DM' },
         { value:'Dominican Republic', code:'DO' },
         { value:'Ecuador', code:'EC' },
         { value:'Egypt', code:'EG' },
         { value:'El Salvador', code:'SV' },
         { value:'Equatorial Guinea', code:'GQ' },
         { value:'Eritrea', code:'ER' },
         { value:'Estonia', code:'EE' },
         { value:'Ethiopia', code:'ET' },
         { value:'Falkland Islands (Malvinas)', code:'FK' },
         { value:'Faroe Islands', code:'FO' },
         { value:'Fiji', code:'FJ' },
         { value:'Finland', code:'FI' },
         { value:'France', code:'FR' },
         { value:'French Guiana', code:'GF' },
         { value:'French Polynesia', code:'PF' },
         { value:'French Southern Territories', code:'TF' },
         { value:'Gabon', code:'GA' },
         { value:'Gambia', code:'GM' },
         { value:'Georgia', code:'GE' },
         { value:'Germany', code:'DE' },
         { value:'Ghana', code:'GH' },
         { value:'Gibraltar', code:'GI' },
         { value:'Greece', code:'GR' },
         { value:'Greenland', code:'GL' },
         { value:'Grenada', code:'GD' },
         { value:'Guadeloupe', code:'GP' },
         { value:'Guam', code:'GU' },
         { value:'Guatemala', code:'GT' },
         { value:'Guernsey', code:'GG' },
         { value:'Guinea', code:'GN' },
         { value:'Guinea-Bissau', code:'GW' },
         { value:'Guyana', code:'GY' },
         { value:'Haiti', code:'HT' },
         { value:'Heard Island and Mcdonald Islands', code:'HM' },
         { value:'Holy See (Vatican City State)', code:'VA' },
         { value:'Honduras', code:'HN' },
         { value:'Hong Kong', code:'HK' },
         { value:'Hungary', code:'HU' },
         { value:'Iceland', code:'IS' },
         { value:'India', code:'IN' },
         { value:'Indonesia', code:'ID' },
         { value:'Iran, Islamic Republic Of', code:'IR' },
         { value:'Iraq', code:'IQ' },
         { value:'Ireland', code:'IE' },
         { value:'Isle of Man', code:'IM' },
         { value:'Israel', code:'IL', searchBy:'holy land, desert' },
         { value:'Italy', code:'IT' },
         { value:'Jamaica', code:'JM' },
         { value:'Japan', code:'JP' },
         { value:'Jersey', code:'JE' },
         { value:'Jordan', code:'JO' },
         { value:'Kazakhstan', code:'KZ' },
         { value:'Kenya', code:'KE' },
         { value:'Kiribati', code:'KI' },
         { value:'Korea, Democratic People\'S Republic of', code:'KP' },
         { value:'Korea, Republic of', code:'KR' },
         { value:'Kuwait', code:'KW' },
         { value:'Kyrgyzstan', code:'KG' },
         { value:'Lao People\'S Democratic Republic', code:'LA' },
         { value:'Latvia', code:'LV' },
         { value:'Lebanon', code:'LB' },
         { value:'Lesotho', code:'LS' },
         { value:'Liberia', code:'LR' },
         { value:'Libyan Arab Jamahiriya', code:'LY' },
         { value:'Liechtenstein', code:'LI' },
         { value:'Lithuania', code:'LT' },
         { value:'Luxembourg', code:'LU' },
         { value:'Macao', code:'MO' },
         { value:'Macedonia, The Former Yugoslav Republic of', code:'MK' },
         { value:'Madagascar', code:'MG' },
         { value:'Malawi', code:'MW' },
         { value:'Malaysia', code:'MY' },
         { value:'Maldives', code:'MV' },
         { value:'Mali', code:'ML' },
         { value:'Malta', code:'MT' },
         { value:'Marshall Islands', code:'MH' },
         { value:'Martinique', code:'MQ' },
         { value:'Mauritania', code:'MR' },
         { value:'Mauritius', code:'MU' },
         { value:'Mayotte', code:'YT' },
         { value:'Mexico', code:'MX' },
         { value:'Micronesia, Federated States of', code:'FM' },
         { value:'Moldova, Republic of', code:'MD' },
         { value:'Monaco', code:'MC' },
         { value:'Mongolia', code:'MN' },
         { value:'Montserrat', code:'MS' },
         { value:'Morocco', code:'MA' },
         { value:'Mozambique', code:'MZ' },
         { value:'Myanmar', code:'MM' },
         { value:'Namibia', code:'NA' },
         { value:'Nauru', code:'NR' },
         { value:'Nepal', code:'NP' },
         { value:'Netherlands', code:'NL' },
         { value:'Netherlands Antilles', code:'AN' },
         { value:'New Caledonia', code:'NC' },
         { value:'New Zealand', code:'NZ' },
         { value:'Nicaragua', code:'NI' },
         { value:'Niger', code:'NE' },
         { value:'Nigeria', code:'NG' },
         { value:'Niue', code:'NU' },
         { value:'Norfolk Island', code:'NF' },
         { value:'Northern Mariana Islands', code:'MP' },
         { value:'Norway', code:'NO' },
         { value:'Oman', code:'OM' },
         { value:'Pakistan', code:'PK' },
         { value:'Palau', code:'PW' },
         { value:'Palestinian Territory, Occupied', code:'PS' },
         { value:'Panama', code:'PA' },
         { value:'Papua New Guinea', code:'PG' },
         { value:'Paraguay', code:'PY' },
         { value:'Peru', code:'PE' },
         { value:'Philippines', code:'PH' },
         { value:'Pitcairn', code:'PN' },
         { value:'Poland', code:'PL' },
         { value:'Portugal', code:'PT' },
         { value:'Puerto Rico', code:'PR' },
         { value:'Qatar', code:'QA' },
         { value:'Reunion', code:'RE' },
         { value:'Romania', code:'RO' },
         { value:'Russian Federation', code:'RU' },
         { value:'RWANDA', code:'RW' },
         { value:'Saint Helena', code:'SH' },
         { value:'Saint Kitts and Nevis', code:'KN' },
         { value:'Saint Lucia', code:'LC' },
         { value:'Saint Pierre and Miquelon', code:'PM' },
         { value:'Saint Vincent and the Grenadines', code:'VC' },
         { value:'Samoa', code:'WS' },
         { value:'San Marino', code:'SM' },
         { value:'Sao Tome and Principe', code:'ST' },
         { value:'Saudi Arabia', code:'SA' },
         { value:'Senegal', code:'SN' },
         { value:'Serbia and Montenegro', code:'CS' },
         { value:'Seychelles', code:'SC' },
         { value:'Sierra Leone', code:'SL' },
         { value:'Singapore', code:'SG' },
         { value:'Slovakia', code:'SK' },
         { value:'Slovenia', code:'SI' },
         { value:'Solomon Islands', code:'SB' },
         { value:'Somalia', code:'SO' },
         { value:'South Africa', code:'ZA' },
         { value:'South Georgia and the South Sandwich Islands', code:'GS' },
         { value:'Spain', code:'ES' },
         { value:'Sri Lanka', code:'LK' },
         { value:'Sudan', code:'SD' },
         { value:'Suriname', code:'SR' },
         { value:'Svalbard and Jan Mayen', code:'SJ' },
         { value:'Swaziland', code:'SZ' },
         { value:'Sweden', code:'SE' },
         { value:'Switzerland', code:'CH' },
         { value:'Syrian Arab Republic', code:'SY' },
         { value:'Taiwan', code:'TW' },
         { value:'Tajikistan', code:'TJ' },
         { value:'Tanzania, United Republic of', code:'TZ' },
         { value:'Thailand', code:'TH' },
         { value:'Timor-Leste', code:'TL' },
         { value:'Togo', code:'TG' },
         { value:'Tokelau', code:'TK' },
         { value:'Tonga', code:'TO' },
         { value:'Trinidad and Tobago', code:'TT' },
         { value:'Tunisia', code:'TN' },
         { value:'Turkey', code:'TR' },
         { value:'Turkmenistan', code:'TM' },
         { value:'Turks and Caicos Islands', code:'TC' },
         { value:'Tuvalu', code:'TV' },
         { value:'Uganda', code:'UG' },
         { value:'Ukraine', code:'UA' },
         { value:'United Arab Emirates', code:'AE' },
         { value:'United Kingdom', code:'GB' },
         { value:'United States', code:'US' },
         { value:'United States Minor Outlying Islands', code:'UM' },
         { value:'Uruguay', code:'UY' },
         { value:'Uzbekistan', code:'UZ' },
         { value:'Vanuatu', code:'VU' },
         { value:'Venezuela', code:'VE' },
         { value:'Viet Nam', code:'VN' },
         { value:'Virgin Islands, British', code:'VG' },
         { value:'Virgin Islands, U.S.', code:'VI' },
         { value:'Wallis and Futuna', code:'WF' },
         { value:'Western Sahara', code:'EH' },
         { value:'Yemen', code:'YE' },
         { value:'Zambia', code:'ZM' },
         { value:'Zimbabwe', code:'ZW' }
      ],
      dropdown : {
         enabled: 1, // suggest tags after a single character input
         classname : 'extra-properties' // custom class for the suggestions dropdown
      } // map tags' values to this property name, so this property will be the actual value and not the printed value on the screen
   })

   tagify_twp.on('click', function(e){
      console.log(e.detail);
   });

   tagify_twp.on('remove', function(e){
      console.log(e.detail);
   });

   tagify_twp.on('add', function(e){
      console.log( "original Input:", tagify_twp.DOM.originalInput);
      console.log( "original Input's value:", tagify_twp.DOM.originalInput.value);
      console.log( "event detail:", e.detail);
   });

   // add the first 2 tags and makes them readonly
   const tagsToAdd = tagify_twp.whitelist.slice(0, 2)
   tagify_twp.addTags(tagsToAdd)

}
tagsWithProperties();






// READONLY
// ---------------------------------------------------------------------------------
const tagify_readonly = document.querySelector('input[name=tags4]');
const tagify_ro = new Tagify(tagify_readonly);





// READONLY MIX
// ---------------------------------------------------------------------------------
const tagify_readonly_mix = document.querySelector('input[name=tags-readonly-mix]');
const tagify_rom = new Tagify(tagify_readonly_mix);





// DISABLED
// ---------------------------------------------------------------------------------
const tagify_disabled = document.querySelector('input[name=disabledInput]');
const tagify_d = new Tagify(tagify_disabled);




function singleValueSelect(){
   // Single Value select
   const tagify_single_value = document.querySelector('input[name=tags-select-mode]');
   const tagify_svs = new Tagify(tagify_single_value, {
      enforceWhitelist: true,
      mode : "select",
      whitelist: ["first option", "second option", "third option"],
      blacklist: ['foo', 'bar'],
   });

   // bind events
   tagify_svs.on('add', onAddTag)
   tagify_svs.DOM.input.addEventListener('focus', onSelectFocus)

   function onAddTag(e){
      console.log(e.detail)
   }

   function onSelectFocus(e){
      console.log(e)
   }
}
singleValueSelect();
