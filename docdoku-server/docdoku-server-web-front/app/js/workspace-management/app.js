/*global define,App*/
define([
    'backbone',
    'mustache',
    'text!templates/content.html',
    'views/workspace-creation',
    'views/workspace-home',
    'views/workspace-management-home'
], function (Backbone, Mustache, template, WorkspaceCreationView, WorkspaceHomeView, WorkspaceManagementHomeView) {
	'use strict';
    var AppView = Backbone.View.extend({

        el: '#content',

        events: {
            'click .new-workspace':'navigateWorkspaceCreation',
            'click .workspace-management':'navigateWorkspaceManagement',
        },

        initialize: function () {
        },

        render: function () {
            var isEditionRegex = new RegExp('#/workspace/'+App.config.workspaceId+'/edit','g');
            var isUsersRegex = new RegExp('#/workspace/'+App.config.workspaceId+'/users','g');
            var isDashboardRegex = new RegExp('#/workspace/'+App.config.workspaceId+'/dashboard','g');
            this.$el.html(Mustache.render(template, {
                administratedWorkspaces:App.config.workspaces.administratedWorkspaces,
                nonAdministratedWorkspaces:App.config.workspaces.nonAdministratedWorkspaces,
                workspaceId:App.config.workspaceId,
                i18n: App.config.i18n,
                isCreation: window.location.hash === '#/create',
                isEdition: window.location.hash.match(isEditionRegex) != null,
                isUsers: window.location.hash.match(isUsersRegex) != null,
                isDashboard: window.location.hash.match(isDashboardRegex) != null,
            })).show();
            return this;
        },

        navigateWorkspaceCreation:function(){
            window.location.hash = '#/create';
        },

        navigateWorkspaceManagement:function(){
            window.location.hash = '#/';
        },

        workspaceManagementHome : function(){
            var view = new WorkspaceManagementHomeView();
            view.render();
            this.$('#workspace-management-content').html(view.$el);
        },

        workspaceCreation : function(){
            var view = new WorkspaceCreationView();
            view.render();
            this.$('#workspace-management-content').html(view.$el);
        },

        workspaceHome : function(){
            var view = new WorkspaceHomeView();
            view.render();
            this.$('#workspace-management-content').html(view.$el);
        }

    });

    return AppView;
});
