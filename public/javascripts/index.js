var editor;

$(document).ready(function() {
    editor = new $.fn.dataTable.Editor( {
        ajax: {
            create: {
                type: 'POST',
                contentType: 'application/json',
                url:  '/api/school-classes',
                data: function ( d ) {
                    console.log(d);
                    var tmp = d['data'];
                    d = tmp;
                    return JSON.stringify( d[0] );
                }
            },
            edit: {
                type: 'PUT',
                contentType: 'application/json',
                url:  '/api/school-classes',
                data: function ( d ) {
                    var ls = Object.values(d['data']);
                    return JSON.stringify( ls );
                },
            },
            remove: {
                type: 'DELETE',
                url:  '/api/school-classes'
            }
        },
        table: "#myTable",
        fields: [ {
            label: "Id:",
            name: "id"
        }, {
            label: "Grade:",
            name: "grade"
        }, {
            label: "Name:",
            name: "name"
        }, {
            label: "Workbook language:",
            name: "workbookLanguage"
        }, {
            label: "Is operational:",
            name: "isOperational"
        }, {
            label: "For School:",
            name: "forSchool"
        }, {
            label: "Ruid:",
            name: "ruid"
        }, {
            label: "Last update time:",
            name: "lastUpdateTime",
            type: "datetime"
        }, {
            label: "Is Deleted:",
            name: "isDeleted"
        }
        ]
    } );

    $('#myTable').DataTable( {
        dom: "Bfrtip",
        ajax: "/api/school-classes",
        columns: [
            { data: null, render: function ( data, type, row ) {
                    // Combine the first and last names into a single table field
                    return data.id;
                } },
            { data: "grade" },
            { data: "name" },
            { data: "workbookLanguage" },
            { data: "isOperational" },
            { data: "forSchool" },
            { data: "ruid" },
            { data: "lastUpdateTime" },
            { data: "isDeleted" },
            // { data: "salary", render: $.fn.dataTable.render.number( ',', '.', 0, '$' ) }
        ],
        select: true,
        buttons: [
            { extend: "create", editor: editor },
            { extend: "edit",   editor: editor },
            { extend: "remove", editor: editor }
        ]
    } );
} );
