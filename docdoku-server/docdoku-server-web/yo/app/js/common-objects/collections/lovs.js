/*global define*/
define([
    'backbone',
    'common-objects/models/lov/lov'
], function (Backbone, lov) {
    'use strict';
    var LOVCollection = Backbone.Collection.extend({

        model: lov,

        url: App.config.contextPath + '/api/workspaces/' + App.config.workspaceId + ''

    });

    return LOVCollection;
});