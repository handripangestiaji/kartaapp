<!DOCTYPE html>
<html lang="<?php echo $this->language; ?>" itemscope itemtype="http://schema.org/Product">
    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
		<?php 
			if($this->document->getTitle()) {
		    // use the manually set title if available
		    $this->headTitle()->set($this->document->getTitle());
			}
			
		
			if($this->document->getDescription()) {
			    // use the manually set description if available
			    $this->headMeta()->appendName('description', $this->document->getDescription());
			}
			
			if($this->document->getKeywords()) {
			    $this->headMeta()->appendName('keywords', $this->document->getKeywords());
			}
	
	        /* $this->headTitle()->append("Asuransi Indonesia Terbaik");
	        $this->headTitle()->setSeparator(" : "); */
	
	        echo $this->headTitle();
	        echo $this->headMeta();
		?>
		<!--
        <title>Karta | The ultimate food discovery app. Coming to Washington, DC.</title>
        <meta name="description" content="Discover and search restaurants and dishes around you. Filter by your dietary restriction. Rate and review any dish on any menu.">

        <!-- FACEBOOK OPEN GRAPH -->
        <meta property="og:title" content="Karta | The ultimate food discovery app. Coming to Washington, DC."/>
        <meta property="og:type" content="website"/>
        <meta property="og:image" content="/website/static/img/og-image.jpg"/>
        <meta property="og:url" content=""/>
        <meta property="og:description" content="Discover and search restaurants and dishes around you. Filter by your dietary restriction. Rate and review any dish on any menu."/>
        <meta property="og:site_name" content="Karta"/>
        <meta property="og:locale" content="en_US" />
        <meta property=”fb:admins” content=”USER_ID”/>
        <meta property="article:author" content="https://www.facebook.com/kartaapp" />
        <meta property="article:publisher" content="https://www.facebook.com/kartaapp" />

        <!-- TWITTER CARD -->
        <meta name="twitter:card" content="summary">
        <meta name="twitter:url" content="">
        <meta name="twitter:title" content="Karta | The ultimate food discovery app. Coming to Washington, DC.">
        <meta name="twitter:description" content="Discover and search restaurants and dishes around you. Filter by your dietary restriction. Rate and review any dish on any menu.">
        <meta name="twitter:image" content="/website/static/img/og-image.jpg">

        <!-- SCHEMA.ORG - https://developers.google.com/+/web/snippet/ -->
        <meta itemprop="name" content="Karta | The ultimate food discovery app. Coming to Washington, DC.">
        <meta itemprop="description" content="Discover and search restaurants and dishes around you. Filter by your dietary restriction. Rate and review any dish on any menu.&quot;">
        <meta itemprop="image" content="/website/static/img/og-image.jpg">

        <link rel="author" href="https://plus.google.com/[YOUR PERSONAL G+ PROFILE HERE]"/>
        <link rel="publisher" href="https://plus.google.com/[YOUR BUSINESS G+ PROFILE HERE]"/>

        <!-- CDN
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
         -->

        <link rel="stylesheet" href="/website/static/css/bootstrap.min.css">
        <link rel="stylesheet" href="/website/static/css/font-awesome.min.css">
        <link rel="stylesheet" href="/website/static/css/style.css">

        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->

    </head>

    <body>

        <header>
            <div class="container">
                <div class="row row-eq-height">
                    <div class="col-xs-12 col-md-2">
                        <a href="/" class="brand">
                            <img src="/website/static/img/logo-white.png" alt="Karta" class="img-responsive">
                        </a><!--/ .brand -->
                    </div><!--/ .col-xs-9 -->
                    <div class="col-xs-12 col-md-4 col-md-offset-6">
                        <ul class="social-icons">
                            <li><a href="https://www.facebook.com/kartaapp" target="_blank"><i class="fa fa-facebook"></i></a></li>
                            <li><a href="https://twitter.com/KartaApp" target="_blank"><i class="fa fa-twitter"></i></a></li>
                            <li><a href="https://www.linkedin.com/company/6399872?trk=tyah&trkInfo=clickedVertical%3Acompany%2Cidx%3A1-1-1%2CtarId%3A1430194116451%2Ctas%3Akarta%20(kartaapp" target="_blank"><i class="fa fa-linkedin"></i></a></li>
                        </ul><!--/ .social-icons -->
                    </div><!--/ .col-xs-3 -->
                </div><!--/ .row -->
            </div><!--/ .container -->
        </header>

        <section class="content">
	        <?php echo $this->layout()->content; ?>
        </section><!--/ .content -->

        <script src="/website/static/js/jquery-1.11.1.min.js"></script>
        <script src="/website/static/js/bootstrap.min.js"></script>
        <script src="/website/static/js/javascripts.js"></script>

    </body>

</html>
