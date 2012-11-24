/*
Copyright (c) 2003-2009, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function(config) {
	// Define changes to default configuration here. For example:
	// config.uiColor = '#AADC6E';
	config.font_names = '宋体;黑体;楷体_GB2312;幼园;'
			+ 'arial;comic sans ms;courier new;georgia;lucida sans unicode;tahoma;'
			+ 'times new roman;trebuchet ms;verdana';

	config.scayt_autoStartup = false;

	config.toolbar_Basic = [ [ 'Bold', 'Italic', 'TextColor' ],
			[ 'Link', 'Unlink' ], [ 'Font', 'FontSize' ], [ 'Smiley' ] ];

	config.toolbar_Full = [
			[ 'Source', '-', 'Save', 'NewPage', 'Preview', '-', 'Tplates' ],
			[ 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Print',
					'SpellChecker', 'Scayt' ],
			[ 'Undo', 'Redo', '-', 'Find', 'Replace', '-', 'SelectAll', 'RoveFormat' ],
			[ 'Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select',
					'Button', 'ImageButton', 'HiddenField' ],
			'/',
			[ 'Bold', 'Italic', 'Underline', 'Strike', '-', 'Subscript',
					'Superscript' ],
			[ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', 'Blockquote' ],
			[ 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ],
			[ 'Link', 'Unlink', 'Anchor' ],
			[ 'Image', 'Flash', 'Table', 'HorizontalRule', 'Smiley', 'SpecialChar',
					'PageBreak' ], '/', [ 'Styles', 'Format', 'Font', 'FontSize' ],
			[ 'TextColor', 'BGColor' ], [ 'Maximize', 'ShowBlocks' ] ];

	config.toolbar_Simple = [ [ 'Source', '-', 'Bold', 'Italic', 'TextColor' ],
			[ 'NumberedList', 'BulletedList' ], [ 'Link', 'Unlink' ],
			[ 'JustifyLeft', 'JustifyCenter', 'JustifyRight' ],
			[ 'Font', 'FontSize' ], [ 'Blockquote', 'Image', 'Smiley' ] ];

	config.toolbar_News = [
			[ 'Source', '-', 'Preview' ],
			[ 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord' ],
			[ 'Undo', 'Redo', '-', 'Find', 'Replace', '-', 'SelectAll', 'RoveFormat' ],
			[ 'Bold', 'Italic', 'Underline', 'Strike', '-', 'Subscript',
					'Superscript' ], [ 'Link', 'Unlink', 'Blockquote' ], '/',
			[ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent' ],
			[ 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ],
			[ 'Image', 'Flash', 'Table', 'Smiley' ],
			[ 'Format', 'Font', 'FontSize' ], [ 'TextColor', 'BGColor' ],
			[ 'Maximize' ] ];

	config.smiley_descriptions = [];

	config.smiley_columns = 15;

	config.smiley_images = [];

	for ( var i = 0; i <= 89; i++) {
		config.smiley_images.push(i + '.gif');
	}
};
