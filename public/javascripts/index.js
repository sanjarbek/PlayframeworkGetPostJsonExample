var editor;

$(document).ready(function() {
    editor = new $.fn.dataTable.Editor( {
        ajax: {
            display: 'jqueryui',
            create: {
                type: 'POST',
                // contentType: 'application/json',
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
                    return ls;
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
            name: "id",
            data: "id",
        }, {
            label: "Grade:",
            name: "grade",
            data: "grade",
        }, {
            label: "Name:",
            name: "name",
            data: "name",
        }, {
            label: "Workbook language:",
            name: "workbookLanguage",
            data: "workbookLanguage",
        }, {
            label: "Is operational:",
            name: "isOperational",
            data: "isOperational",
        }, {
            label: "For School:",
            name: "forSchool",
            data: "forSchool",
        }, {
            label: "Ruid:",
            name: "ruid",
            data: "ruid",
        }, {
            label: "Last update time:",
            name: "lastUpdateTime",
            type: "datetime",
            data: "lastUpdateTime",
        }, {
            label: "Is Deleted:",
            name: "isDeleted",
            data: "isDeleted",
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
