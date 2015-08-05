<html>
<head>
<!-- Meta Tags -->
<meta charset="utf-8">
</head>
<body style="font-size: 100%;background: none repeat scroll 0 0 #f5f6f6;color: #111;
    font-family: Arial,Helvetica,sans-serif;text-align: center;">
<div role="main" class="main no-gutter" style="clear: both;padding: 20px 0 0 10px;width: 100%;padding: 0 !important;">
	<div class="blast-container" style="margin: 0 auto;padding: 0 15px;position: relative;text-align: left;width: 600px;">
		
		<!--jurnal -->
		
		<div id="community no-gutter no-margin" class="clearfix">
			
			<div class="blast-img" style="height: auto;">
                   <!-- <img src="_assets/css/bootstrap/edwin.jpg" /> -->
                   <?php echo $this->image('imgEmail', array(
									'title' 	=> 'Image Size 600px X 400px',
									'width' 	=> 600,
									'height' 	=> 400)) ?>
			</div>
            
		</div> <!--Community-->
		
		<div id="community" class="clearfix padding no-margin" style="background: none repeat scroll 0 0 #fff;clear: both;margin-bottom: 20px;padding: 15px;padding: 15px 34px !important;margin: 0 !important;">
			
            <?php echo $this->wysiwyg('description')?>
            
		</div> <!--Community-->
	</div>
</div>
</body>
</html>