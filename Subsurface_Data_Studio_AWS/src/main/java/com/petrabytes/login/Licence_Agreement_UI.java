package com.petrabytes.login;

import java.io.File;

import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
@Route(value = "licence")
public class Licence_Agreement_UI extends VerticalLayout{
	
	private VerticalLayout mainLayout = new VerticalLayout();
	
	public Licence_Agreement_UI() {
	
		
		SetUI();
		Paragraph termsandconditions = new Paragraph();
		
	    H4 head = new H4();
	    head.add("STANDARD CONTRACT FOR AWS MARKETPLACE");
	    head.getStyle().set("align-self", "center");
	    
	    Header scope = new Header();
	    scope.add("1.    Scope.");
	    termsandconditions.add(
				
				"1.1 Terms and Conditions. This Standard Contract for AWS Marketplace (the “Standard\r\n" + 
				"Contract”) sets forth the terms and conditions applicable to the licensing of Software from the licensor\r\n" + 
				"(“Licensor”) by the Party subscribing to the Software (“Buyer”) through the AWS Marketplace, whether\r\n" + 
				"deployed via AMI or SaaS, via a Standard Contract Listing. The offer of the Software as a Standard Contract\r\n" + 
				"Listing on the AWS Marketplace, and Buyer’s purchase of the corresponding Subscription on the AWS\r\n" + 
				"Marketplace, constitutes each Party’s respective acceptance of this Standard Contract and their entry into this\r\n" + 
				"Agreement (defined below). Unless defined elsewhere in this Standard Contract, terms in initial capital letters\r\n" + 
				"have the meanings set forth in Section 13. Buyer and Licensor may be referred to collectively as the “Parties” or\r\n" + 
				"individually as a “Party”."); 
	   
	   
	    Paragraph softwaresubp = new Paragraph();
	    softwaresubp.add(
	    		"1.2 Software Subscription.     1.2  Buyer will subscribe to a Subscription as set forth in the Standard \r\n "+ 
				"Contract Listing in accordance with this Agreement. Licensor will supply and sell the Subscription to Buyer, or\r\n" + 
				"Buyer may purchase the Subscription from Licensor’s authorized reseller (“Reseller”). A Subscription, as\r\n" + 
				"described in the applicable Standard Contract Listing, may be for Software deployed via AMI (“AMI Software”)\r\n" + 
				"or Software deployed via SaaS (“SaaS Software”). Software may be targeted for specific geographic regions,\r\n" + 
				"and Support Services may vary by geography as set forth in the applicable Standard Contract Listing. A\r\n" + 
				"Subscription may be provided on a Metered Pricing, Entitlement Pricing or other basis through the functionality\r\n" + 
				"available through AWS Services. The fee or rate for the Subscription is set forth in the applicable Standard\r\n" + 
				"Contract Listing. For Subscriptions provided on a Metered Pricing basis, upon request by Buyer, Licensor will\r\n" + 
				"provide sufficient documentation from its books and records to allow Buyer to verify the metered usage charged\r\n" + 
				"to Buyer for the Subscription.");
	    Paragraph taxes = new Paragraph();
	    taxes.add(
				"1.3 Taxes. Each Party will be responsible, as required under applicable Law, for identifying and\r\n" + 
				"paying all taxes and other governmental fees and charges (and any penalties, interest and other additions thereto)\r\n" + 
				"that are imposed on that Party upon or with respect to the transactions and payments under this Agreement.\r\n" + 
				"Applicable taxes and duties may be due in addition to the fees or rates payable by Buyer. Licensor may charge\r\n" + 
				"and Buyer will pay, where applicable, national, state or local sales or use taxes, or value added or goods and\r\n" + 
				"services tax, or withholding or other taxes (“Taxes”). Where required by local legislation, Amazon Web\r\n" + 
				"Services, Inc. may charge for Taxes in its own name for Subscriptions made by Buyers on the AWS Marketplace,\r\n" + 
				"and Buyer will pay such Taxes. Buyer will receive a compliant tax invoice, where required. Licensor will be\r\n" + 
				"responsible for all other taxes or fees arising (including interest and penalties) from transactions and the\r\n" + 
				"documentation of transactions under this Agreement. Upon request, Buyer will provide such information to\r\n" + 
				"Licensor as reasonably required to determine whether Licensor is obligated to collect Taxes from Buyer.\r\n" + 
				"Licensor will not collect (or will refund to Buyer), and Buyer will not be obligated to pay (or will be entitled to a\r\n" + 
				"refund from Licensor), any such Tax or duty for which Buyer furnishes Licensor a properly completed exemption\r\n" + 
				"certificate or a direct payment permit certificate or for which Licensor claims an available exemption from Tax.\r\n" + 
				"Licensor will provide Buyer with any forms, documents or certifications as may be required for Buyer to satisfy\r\n" + 
				"any information reporting or withholding tax obligations with respect to any payments under this Agreement.\r\n" );
	    Paragraph agreement = new Paragraph();
	    agreement.add(
				"1.4 Agreement. Each Subscription is subject to and governed by this Standard Contract, the\r\n"+ 
				"applicable Standard Contract Listing, the terms and conditions of the NDA (if any), the Privacy and Security\r\n" + 
				"Terms for SaaS Subscriptions, and any amendments to any of the foregoing as may be agreed upon by the Parties,\r\n" + 
				"which together constitute the agreement between Buyer and Licensor (the “Agreement”). Each Subscription is a\r\n" + 
				"separate agreement between Buyer and Licensor. In the event of any conflict between the terms and conditions of\r\n" + 
				"the various components of this Agreement, the following order of precedence will apply: (a) any amendment\r\n" + 
				"agreed upon by the parties; (b) the Privacy and Security Terms for SaaS Subscriptions; (c) the NDA (if any); (d)\r\n" );
	    
				Header License = new Header();
				License.add("2. Licenses.");
				
			    Header Licenses = new Header();
			    Licenses.add("2.1 Licensed Materials. ");
				
			    Paragraph licensematerials = new Paragraph();
			    licensematerials.add(
				"2.1.1 If the Subscription is for AMI Software, Licensor hereby grants to Buyer, subject to \r\n" +
				"Section 2.1.3, a nonexclusive, worldwide (subject to Section 12.6), nontransferable (except in connection with an \r\n" +
				"assignment permitted under Section 12.2 or a divestiture permitted under Section 12.3), non-terminable (except as \r\n" +
				"provided in Section 10) license under all Proprietary Rights in and to the AMI Software and AMI Image, to \r\n" +
				"deploy, operate and use the AMI Software and AMI Image under Buyer’s own AWS Services account on AWS \r\n" +
				"Services infrastructure in accordance with the applicable Standard Contract Listing and to allow its Users to \r\n" +
				"access and use the AMI Software and AMI Image as so deployed.\r\n" );
						
			    Paragraph Licensor = new Paragraph();
			    Licensor.add(
				"2.1.2 If the Subscription is for SaaS Software, Licensor hereby grants to Buyer, subject to \r\n" +
				"Section 2.1.3, a nonexclusive, worldwide (subject to Section 12.6), nontransferable (except in connection with an \r\n" +
				"assignment permitted under Section 12.2or a divestiture permitted under Section 12.3), non-terminable (except as \r\n" +
				"provided in Section 10) license under all Proprietary Rights in and to the SaaS Software and SaaS Service, to \r\n" +
				"access, receive and use the SaaS Software and SaaS Service in accordance with the applicable Standard Contract\r\n" +
				"Listing and to allow its Users to access, receive and use the SaaS Software and SaaS Service. \r\n");
				
			    Paragraph connection = new Paragraph();
			    connection.add(
				"2.1.3 Buyer may use the Software and, as applicable, the AMI Image or SaaS Service, only: in \r\n" +
				"support of the internal operations of Buyer’s and its Affiliates’ business(es) or organization(s), in connection with \r\n" +
				"Buyer’s and its Affiliates’ products and services (but, for clarity, not as a stand-alone product or service of Buyer \r\n" +
				"or its Affiliates), and in connection with Buyer’s and its Affiliate’s interactions with Users. \r\n" );
				
			    Paragraph reasonable = new Paragraph();
			    reasonable.add(
				"2.1.4 Buyer may make a reasonable number of copies of the Documentation as necessary to \r\n" +
				"use such Software, and as applicable the AMI Image, in accordance with the rights granted under this Agreement, \r\n" +
				"provided that Buyer includes all proprietary legends and other notices on all copies. Licensor retains all rights not \r\n" +
				"expressly granted to Buyer under this Agreement.\r\n" );
						
			    Paragraph deemed = new Paragraph();
			    deemed.add(
				"2.2 Affiliates and Contractors. With respect to Affiliates and Contractors that Buyer allows to use \r\n" +
				"the Licensed Materials: (a) Buyer remains responsible for all obligations hereunder arising in connection with \r\n" +
				"such Affiliate’s or Contractor’s use of the Licensed Materials; and (b) Buyer agrees to be directly liable for any \r\n" +
				"act or omission by such Affiliate or Contractor to the same degree as if the act or omission were performed by \r\n" +
				"Buyer such that a breach by an Affiliate or a Contractor of the provisions of this Agreement will be deemed to be \r\n" +
				"a breach by Buyer. The performance of any act or omission under this Agreement by an Affiliate or a Contractor \r\n" +
				"for, by or through Buyer will be deemed the act or omission of Buyer.\r\n" );
				
			    Paragraph Restrictions = new Paragraph();
			    Restrictions.add(
				"2.3 Restrictions. Except as specifically provided in this Agreement, Buyer and any other User of \r\n" +
				"any Licensed Materials, in whole or in part, may not: (a) copy the Licensed Materials, in whole or in part; (b) \r\n" +
				"distribute copies of Licensed Materials, in whole or in part, to any third party; (c) modify, adapt, translate, make \r\n" +
				"alterations to or make derivative works based on Licensed Materials or any part thereof; (d) except as permitted \r\n" +
				"by Law, decompile, reverse engineer, disassemble or otherwise attempt to derive source code from the Software; \r\n" +
			"	(e) use, rent, loan, sub-license, lease, distribute or attempt to grant other rights to any part of the Licensed \r\n" +
				"Materials to third parties; (f) use the Licensed Materials to act as a consultant, service bureau or application \r\n" +
				"service provider; or (g) permit access of any kind to the Licensed Materials to any third party.\r\n" );
				
			    Paragraph reverse = new Paragraph();
			    reverse.add(
				"2.4 Open Source Software. Subject to the requirements of Section 5.1(d), Software may contain or \r\n" +
				"be provided with components that are subject to the terms and conditions of “open source” software licenses \r\n" +
				"(“Open Source Software”). If Buyer’s use of the Software subjects Buyer to the terms of any license governing \r\n" +
				"the use of Open Source Software, then information concerning such Open Source Software and the applicable \r\n" +
				"license must be incorporated or referenced in the Standard Contract Listing or Documentation. To the extent \r\n" +
				"required by the license to which the Open Source Software is subject, the terms of such license will apply in lieu \r\n" +
				"of the terms of this Agreement with respect to such Open Source Software, including without limitation, any\r\n" +
				"provisions governing attribution, access to source code, modification and reverse-engineering.\r\n");
						
    Paragraph Additional = new Paragraph();
    Additional.add(
				"2.5 No Additional Terms. No shrink-wrap, click-acceptance or other terms and conditions outside\r\n" + 
				"this Agreement provided with any Licensed Materials or any part thereof (“Additional Terms”) will be binding\r\n" + 
				"on Buyer or its Users, even if use of the Licensed Materials, or any part thereof, requires an affirmative\r\n" + 
				"“acceptance” of such Additional Terms before access to or use of the Licensed Materials, or any part thereof, is\r\n" + 
				"permitted. All such Additional Terms will be of no force or effect and will be deemed rejected by Buyer in their\r\n" + 
				"entirety. For clarity, the Software, Subscription type (AMI or SaaS), fee structure (Entitlement Pricing or\r\n" + 
				"Metered Pricing), technical requirements for use of the Software, Support Services, as well as any information\r\n" + 
				"regarding Open Source Software set forth or referenced in the Standard Contract Listing or Documentation, are\r\n" + 
				"not Additional Terms subject to this Section.\r\n");
				
    Paragraph Activities = new Paragraph();
    Activities.add(
				"2.6 High-Risk Activities. The Software is not designed or developed for use in high-risk, hazardous\r\n" + 
				"environments requiring fail-safe performance, including without limitation in the operation of nuclear facilities,\r\n" + 
				"aircraft navigation or control systems, air traffic control, or weapons systems, or any other application in which\r\n" + 
				"the failure of the Software could lead to severe physical or environmental damages (“High Risk Activities”).\r\n" + 
				"Buyer will not use the Software for High Risk Activities.\r\n" );
			    
			    Header serviceheader = new Header();
			    serviceheader.add("3. Services.");
				
			    Paragraph Saa_Service = new Paragraph();
			    Saa_Service.add(
				 
				"3.1 SaaS Service. If Buyer is purchasing a SaaS Subscription, Licensor will provide the SaaS\r\n" + 
				"Service to Buyer in accordance with the Standard Contract Listing promptly following purchase of the\r\n" + 
				"Subscription and continuing until completion of the Subscription. Licensor will provide Buyer all license keys,\r\n" + 
				"access credentials and passwords necessary for access and use of the Software and SaaS Service (“Keys”) as set\r\n" + 
				"forth in the Standard Contract Listing.\r\n" );
			    Paragraph support_services = new Paragraph();
			    support_services.add(
				"3.2 Support Services. Licensor will provide sufficient Documentation to allow a reasonably\r\n" + 
				"competent user to access and use the Software, and Licensor will provide Support Services to Buyer in\r\n" + 
				"accordance with the support plan set forth or incorporated into the Standard Contract Listing.\r\n" + 
				"4. Proprietary Rights.\r\n" + 
				"4.1 Licensed Materials. Subject to the licenses granted herein, Licensor will retain all right, title and\r\n" + 
				"interest it may have in and to the Licensed Materials, including all Proprietary Rights therein. Nothing in this\r\n" + 
				"Agreement will be construed or interpreted as granting to Buyer any rights of ownership or any other proprietary\r\n" + 
				"rights in or to the Licensed Materials.\r\n" );
			    
			    Paragraph feedback = new Paragraph();
			    feedback.add(
				"4.2 Feedback. If Buyer provides any suggestions, ideas, enhancement requests, recommendations or\r\n" + 
				"feedback regarding the Licensed Materials or Support Services (“Feedback”), Licensor may use and incorporate\r\n" + 
				"Feedback in Licensor’s products and services. Buyer will have no obligation to provide Feedback, and all\r\n" + 
				"Feedback is provided by Buyer “as is” and without warranty of any kind.\r\n");
			    
			    Header warrenty = new Header();
			    warrenty.add("5. Warranties.");
			    
			    Paragraph warranties = new Paragraph();
			    warranties.add(
				
				"5.1 Licensed Materials. Licensor represents and warrants that: (a) for Subscriptions with\r\n" + 
				"Entitlement Pricing, the Software, and as applicable the AMI Image or SaaS Service, will conform, in all material\r\n" + 
				"respects, to the Documentation during the Warranty Period; (b) AMI Software will not contain any automatic\r\n" + 
				"shut-down, lockout, “time bomb” or similar mechanisms that could interfere with Buyer’s exercise of its rights\r\n" + 
				"under this Agreement (for clarity, the foregoing does not prohibit license keys that expire at the end of the\r\n" + 
				"Subscription); (c) Licensor will use industry standard practices designed to detect and protect the Software against\r\n" + 
				"any viruses, “Trojan horses”, “worms”, spyware, adware or other harmful code designed or used for unauthorized\r\n" + 
				"access to or use, disclosure, modification or destruction of information within the Software or interference with or\r\n" + 
				"harm to the operation of the Software or any systems, networks or data, including as applicable using anti-\r\n" + 
				"malware software and keeping the anti-malware software up to date prior to making the Software (including any\r\n" + 
				"Software provided through Support Services) available to Buyer, and for SaaS Software, scanning the SaaS\r\n" + 
				"Software on a regular basis; (d) the Software, and Buyer’s use thereof as permitted under this Agreement, will not\r\n" + 
				"be subject to any license or other terms that require that any Buyer Data, Buyer Materials or any software,\r\n" + 
				"documentation, information or other materials integrated, networked or used by Buyer with the Software, in\r\n" + 
				"whole or in part, be disclosed or distributed in source code form, be licensed for the purpose of making derivative\r\n" + 
				"works, or be redistributable at no charge; and (e) the Software, and as applicable the AMI Image or SaaS Service,\r\n" + 
				"will conform, to the extent applicable, with then-current Web Content Accessibility Guidelines (WCAG) and any\r\n" + 
				"other applicable accessibility Laws.\r\n");
			    
			    Paragraph services = new Paragraph();
			    services.add(
				"5.2 Services. Licensor represents and warrants that the Services will be performed in a professional\r\n" + 
				"manner with a level of care, skill and diligence performed by experienced and knowledgeable professionals in the\r\n" + 
				"performance of similar services.\r\n" );
			    
			    Paragraph remedies = new Paragraph();
			    remedies.add(
				"5.3 Remedies. If any Software or Service fails to conform to the foregoing warranties, Licensor\r\n" + 
				"promptly will, at its option and expense, correct the Software and re-perform the Services as necessary to conform\r\n" + 
				"to the warranties. If Licensor does not correct the Software or re-perform the Services to conform to the\r\n" + 
				"warranties within a reasonable time, not to exceed 30 days, as Buyer’s sole remedy and Licensor’s exclusive\r\n" + 
				"liability (except as provided in Section 9), Buyer may terminate the Subscription and this Agreement without\r\n" + 
				"further liability and Licensor will provide Buyer with a refund of any fees prepaid to Licensor by Buyer, prorated\r\n" + 
				"for the unused portion of the Subscription, as well as, if applicable, any service credits available under Licensor’s\r\n" + 
				"Support Services or other policies.\r\n" );
			    
			    Paragraph special_remedy = new Paragraph();
			    special_remedy.add(
				"5.4 Special Remedy for Certain Entitlement Pricing Subscriptions. This Section applies only to a\r\n" + 
				"Subscription with Entitlement Pricing that is $100,000 or more other than an Excluded Subscription. “Excluded\r\n" + 
				"Subscription” means a Subscription: (a) with Metered Pricing; (b) for software for which Licensor also offers\r\n" + 
				"free trial use, whether subject to the Standard Contract or other terms and conditions; (c) that is a renewal of an\r\n" + 
				"expiring subscription, or a new subscription for software previously licensed from Licensor by Buyer, whether on\r\n" + 
				"a paid, free or trial basis, and whether subject to the Standard Contract or other terms and conditions; or (d) that\r\n" + 
				"increases the quantity of Buyer’s then-current use of such software (e.g., additional hosts, CPU capacity, users or\r\n" + 
				"other metric of quantity). If, for any Subscription with Entitlement Pricing that is $100,000 or more other than an\r\n" + 
				"Excluded Subscription, Buyer reports a breach of the warranty set forth in Section 5.1(a) during the first 30 days\r\n" + 
				"of the Warranty Period, and if, following the process set forth in Section 5.3, the Software does not operate as\r\n" + 
				"warranted under Section 5.1(a), then as Buyer’s sole remedy and Licensor’s exclusive liability in lieu of the\r\n" + 
				"remedy available under Section 5.3, Buyer may terminate the Subscription or this Agreement without further\r\n" + 
				"liability and Licensor will provide Buyer with a full refund of all fees paid to Licensor by Buyer for the\r\n" + 
				"Subscription.\r\n" );
			    

			    Paragraph warranty_exclusions = new Paragraph();
			    warranty_exclusions.add(
				"5.5 Warranty Exclusions. Licensor will have no liability or obligation with respect to any warranty\r\n" + 
				"to the extent attributable to any: (a) use of the Software by Buyer in violation of this Agreement or applicable\r\n" + 
				"Law; (b) unauthorized modifications to the Licensed Materials made by Buyer or its Personnel; (c) use of the\r\n" + 
				"Software in combination with third-party equipment or software not provided or made accessible by Licensor or\r\n" + 
				"contemplated by the Standard Contract Listing or Documentation; or (d) use by Buyer of Software in conflict with\r\n" + 
				"the Documentation, to the extent that such nonconformity would not have occurred absent such use or\r\n" + 
				"modification by Buyer.\r\n" );
				

			    Paragraph compliance = new Paragraph();
			    compliance.add(
				"5.6 Compliance with Laws. Each Party represents and warrants that it will comply with all\r\n" + 
				"applicable international, national, state and local laws, ordinances, rules, regulations and orders, as amended from\r\n" + 
				"time to time (“Laws”) applicable to such Party in its performance under this Agreement.\r\n" );


			    Paragraph Power = new Paragraph();
			    Power.add(
				"5.7 Power and Authority. Each Party represents and warrants that: (a) it has full power and\r\n" + 
				"authority to enter in and perform this Agreement and that the execution and delivery of this Agreement has been\r\n" + 
				"duly authorized; and (b) this Agreement and such Party’s performance hereunder will not breach any other\r\n" + 
				"agreement to which the Party is a party or is bound or violate any obligation owed by such Party to any third\r\n" + 
				"party.\r\n"); 


			    Paragraph disclaimer = new Paragraph();
			    disclaimer.add(
				"5.8 Disclaimer. EXCEPT FOR THE WARRANTIES SPECIFIED IN THIS AGREEMENT,\r\n" + 
				"NEITHER PARTY MAKES ANY WARRANTIES, EITHER EXPRESS OR IMPLIED, INCLUDING, BUT\r\n" + 
				"NOT LIMITED TO, ANY IMPLIED WARRANTIES OF MERCHANTABILITY OR FITNESS FOR A\r\n" + 
				"PARTICULAR PURPOSE, REGARDING THE LICENSED MATERIALS, SERVICES, BUYER MATERIALS\r\n" + 
				"AND BUYER DATA, AND EACH PARTY HEREBY DISCLAIMS ALL OTHER WARRANTIES, EXPRESS\r\n" + 
				"OR IMPLIED, INCLUDING WITHOUT LIMITATION, WARRANTIES OF MERCHANTABILITY, FITNESS\r\n" + 
				"FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. Licensor does not warrant: (a) that the\r\n" + 
				"Licensed Materials will meet Buyer’s requirements; or (b) that the operation of the Software will be uninterrupted\r\n" + 
				"or error free.\r\n" );
			    
			    Header confidentiality = new Header();
			    confidentiality.add("6. Confidentiality.");
				
			    Paragraph confidential = new Paragraph();
			    confidential.add(
				"6.1 Confidential Information. “Confidential Information” means any nonpublic information\r\n" + 
				"directly or indirectly disclosed by either Party (the “Disclosing Party”) to the other Party (the “Receiving\r\n" + 
				"Party”) or accessible to the Receiving Party pursuant to this Agreement that is designated as confidential or that,\r\n" + 
				"given the nature of the information or the circumstances surrounding its disclosure, reasonably should be\r\n" + 
				"considered as confidential, including without limitation technical data, trade secrets, know-how, research,\r\n" + 
				"inventions, processes, designs, drawings, strategic roadmaps, product plans, product designs and architecture,\r\n" + 
				"security information, marketing plans, pricing and cost information, marketing and promotional activities,\r\n" + 
				"business plans, customer and supplier information, employee and User information, business and marketing plans,\r\n" + 
				"and business processes, and other technical, financial or business information, and any third party information that\r\n" + 
				"the Disclosing Party is required to maintain as confidential. Confidential Information will not, however, include\r\n" + 
				"any information which: (a) was publicly known or made generally available to the public prior to the time of\r\n" + 
				"disclosure; (b) becomes publicly known or made generally available after disclosure through no fault of the\r\n" + 
				"Receiving Party; (c) is in the possession of the Receiving Party, without restriction as to use or disclosure, at the\r\n" + 
				"time of disclosure by the Disclosing Party; (d) was lawfully received, without restriction as to use or disclosure,\r\n" + 
				"from a third party (who does not have an obligation of confidentiality or restriction on use itself); or (e) is\r\n" + 
				"developed by the Receiving Party independently from this Agreement and without use of or reference to the\r\n" + 
				"Disclosing Party’s Confidential Information or Proprietary Rights. Except for rights expressly granted in this\r\n" + 
				"Agreement, each Party reserves all rights in and to its Confidential Information. The Parties agree that the\r\n" + 
				"Licensed Materials are Confidential Information of Licensor.\r\n" );

			    Paragraph obligations = new Paragraph();
			    obligations.add(
				"6.2 Obligations. The Parties will maintain as confidential and will avoid disclosure and\r\n" + 
				"unauthorized use of Confidential Information of the other Party using reasonable precautions. Each Party will\r\n" + 
				"protect such Confidential Information with the same degree of care that a prudent person would exercise to\r\n" + 
				"protect its own confidential information of a like nature, and to prevent the unauthorized, negligent, or inadvertent\r\n" + 
				"use, disclosure, or publication thereof or access thereto. Each Party will restrict Confidential Information to\r\n" + 
				"individuals who need to know such Confidential Information and who are bound to confidentiality obligations at\r\n" + 
				"least as protective as the restrictions described in this Section 6. Except as necessary for the proper use of the\r\n" + 
				"Software, the exercise of a Party’s rights under this Agreement, performance of a Party’s obligations under this\r\n" + 
				"Agreement or as otherwise permitted under this Agreement, neither Party will use Confidential Information of the\r\n" + 
				"other Party for any purpose except in fulfilling its obligations or exercising its rights under this Agreement. Each\r\n" + 
				"Party will promptly notify the other Party if it becomes aware of any unauthorized use or disclosure of the other\r\n" + 
				"Party’s Confidential Information, and reasonably cooperate with the other Party in attempts to limit disclosure.\r\n");
				
				Paragraph compelleddisclosure = new Paragraph();
				compelleddisclosure.add(		
				"6.3 Compelled Disclosure. If and to the extent required by law, including regulatory requirements,\r\n"+
				"discovery request, subpoena, court order or governmental action, the Receiving Party may disclose or produce\r\n" + 
				"Confidential Information but will give reasonable prior notice (and where prior notice is not permitted by\r\n" + 
				"applicable Law, notice will be given as soon as the Receiving Party is legally permitted) to the Disclosing Party to\r\n" + 
				"permit the Disclosing Party to intervene and to request protective orders or confidential treatment therefor or other\r\n" + 
				"appropriate remedy regarding such disclosure. Disclosure of any Confidential Information pursuant to any legal\r\n" + 
				"requirement will not be deemed to render it non-confidential, and the Receiving Party’s obligations with respect\r\n" + 
				"to Confidential Information of the Disclosing Party will not be changed or lessened by virtue of any such\r\n" + 
				"disclosure.\r\n" );
				
				Paragraph buyer = new Paragraph();
				buyer.add(
				"6.4 NDA. Buyer and Licensor may agree that a separate nondisclosure agreement between Buyer\r\n" + 
				"and Licensor (or the respective Affiliates of Buyer and Licensor) (“NDA”) will apply to the Subscription, in\r\n" + 
				"which case the terms and conditions thereof are incorporated herein by reference and will apply instead of\r\n" + 
				"subsections 6.1 through 6.3 of this Section 6.\r\n" );
				
				Header saas = new Header();
						saas.add("7. Additional SaaS Service Obligations and Responsibilities. This Section 7 applies to Subscriptions for\r\n" + 
				"SaaS Software and SaaS Service only.\r\n");
						
				Paragraph acceptable_use = new Paragraph();
				acceptable_use.add(
				"7.1 Acceptable Use. Buyer will not intentionally use the SaaS Software or SaaS Service to: (a) store,\r\n" + 
				"download or transmit infringing or illegal content, or any viruses, “Trojan horses” or other harmful code; (b)\r\n" + 
				"engage in phishing, spamming, denial-of-service attacks or fraudulent or criminal activity; (c) interfere with or\r\n" + 
				"disrupt the integrity or performance of the Software or data contained therein or on Licensor’s system or network;\r\n" + 
				"or (d) perform penetration testing, vulnerability testing or other security testing on the Software or Licensor’s\r\n" + 
				"systems or networks or otherwise attempt to gain unauthorized access to the Software or Licensor’s systems or\r\n" + 
				"networks.\r\n" );
					
				Paragraph buyer_materials = new Paragraph();
				buyer_materials.add(
				"7.2 Buyer Data and Buyer Materials.\r\n" + 
				"7.2.1 Buyer is and will continue to be the sole and exclusive owner of all Buyer Materials,\r\n" + 
				"Buyer Data and other Confidential Information of Buyer, including all Proprietary Rights therein. Nothing in this\r\n" + 
				"Agreement will be construed or interpreted as granting to Licensor any rights of ownership or any other\r\n" + 
				"proprietary rights in or to the Buyer Data and Buyer Materials.\r\n" );
				
				Paragraph authorization = new Paragraph();
				authorization.add(
				"7.2.2 Buyer will obtain all necessary consents, authorizations and rights and provide all\r\n" + 
				"necessary notifications in order to provide Buyer Data to Licensor and for Licensor to use Buyer Data in the\r\n" + 
				"performance of its obligations in accordance with the terms and condition of this Agreement, including any access\r\n" + 
				"or transmission to third parties with whom Buyer shares or permits access to Buyer Data.\r\n" );
				
				Paragraph information = new Paragraph();
				information.add(
				"7.2.3 The Parties agree that Buyer Data and Buyer Materials are Confidential Information of\r\n" + 
				"Buyer. Buyer hereby grants to Licensor a nonexclusive, nontransferable (except in connection with an\r\n" + 
				"assignment permitted under Section 12.2), revocable license, under all Proprietary Rights, to reproduce and use\r\n" + 
				"Buyer Materials and Buyer Data solely for the purpose of, and to the extent necessary for, performing Licensor’s\r\n" + 
				"obligations under this Agreement. In no event will Licensor access, use or disclose to any third party any Buyer\r\n" + 
				"Data or any Buyer Materials for any purpose whatsoever (including, without limitation, the marketing of\r\n" + 
				"Licensor’s other products or services) other than as necessary for the purpose of providing the Software and\r\n" + 
				"Services to Buyer and performing its obligations under this Agreement. Licensor will not aggregate, anonymize\r\n" + 
				"or create any data derivatives of Buyer Data other than as necessary to provide the Software or Services and to\r\n" + 
				"perform its obligations in accordance with the terms and conditions of this Agreement.\r\n" ); 
				
				Paragraph entirety = new Paragraph();
				entirety.add(
				"7.2.4 Buyer will have full access to, and has the right to review and retain, the entirety of\r\n" + 
				"Buyer Data contained in the Software. At no time will any computer or electronic records containing Buyer Data\r\n" + 
				"be stored or held in a form or manner not readily accessible to Buyer through the ordinary operation of the\r\n" + 
				"Software. Licensor will provide to Buyer all passwords, codes, comments, keys and documentation necessary for\r\n" + 
				"such access and use of the Software, and Buyer will be entitled to delete, or have Licensor delete, Buyer Data as\r\n" + 
				"expressly specified by Buyer.\r\n" );
				
				Paragraph conjunction = new Paragraph();
				conjunction.add(
				"7.3 System Data. To the extent that System Data identifies or permits, alone or in conjunction with\r\n" + 
				"other data, identification, association, or correlation of or with Buyer, its Affiliates, Users, customers, suppliers or\r\n" + 
				"other persons interacting with any of the foregoing, or any Confidential Information of Buyer or any device as\r\n" + 
				"originating through or interacting with Buyer or its Affiliates (“Identifiable System Data”), Licensor may only\r\n" + 
				"collect and use Identifiable System Data internally to provide and improve the Software and Services and\r\n" + 
				"Licensor’s other products and services. Licensor will not target any data analysis at, or otherwise use any\r\n" + 
				"Identifiable System Data to derive or attempt to derive information regarding, Buyer and its Affiliates, their\r\n" + 
				"businesses, operations, finances, users, customers, prospective customers, suppliers or other persons interacting\r\n" + 
				"with Buyer and its Affiliates. Licensor will not target any development efforts, marketing, communications or\r\n" + 
				"promotions arising from its use of Identifiable System Data at Buyer and its Affiliates or any other person on the\r\n" + 
				"basis of the intended recipient’s relationship with Buyer or any of its Affiliates. Licensor will not use or disclose\r\n" + 
				"any Identifiable System Data for any other purpose unless otherwise agreed in writing by the Parties.\r\n");
				
				Paragraph Notwithstanding = new Paragraph();
				Notwithstanding.add(
				"7.4 Use of Other Data. Notwithstanding the foregoing, nothing in this Agreement will restrict: (a)\r\n" + 
				"Licensor’s use of System Data or data derived from System Data that does not identify or permit, alone or in\r\n" + 
				"conjunction with other data, identification, association, or correlation of or with (i) Buyer, its Affiliates, Users,\r\n" + 
				"customers, suppliers or other persons interacting with Buyer and its Affiliates or any Confidential Information of\r\n" + 
				"Buyer, or (ii) any device (e.g. computer, mobile telephone, or browser) used to access or use the Software as\r\n" + 
				"originating through Buyer or its Affiliates or interacting with Buyer or its Affiliates; or (b) either Party’s use of\r\n" + 
				"any data, records, files, content or other information related to any third party that is collected, received, stored or\r\n" + 
				"maintained by a Party independently from this Agreement.\r\n" );
				
				Paragraph security = new Paragraph();
				security.add(
				"7.5 Security. Licensor will, consistent with industry standard practices, implement and maintain\r\n" + 
				"physical, administrative and technical safeguards and other security measures: (a) to maintain the security and\r\n" + 
				"confidentiality of Buyer Data; and (b) to protect Buyer Data from known or reasonably anticipated threats or\r\n" + 
				"hazards to its security, availability and integrity, including accidental loss, unauthorized use, access, alteration or\r\n" + 
				"disclosure. Without limiting the foregoing, Licensor will provide the SaaS Services in compliance with the\r\n" + 
				"Security Addendum attached hereto.\r\n" );
				
				Header protection = new Header();
				protection.add("7.6 Data Protection Legislation.\r\n" );
				
				Paragraph Legislation = new Paragraph();
				Legislation.add(
				
				"7.6.1 Each Party will comply with all data protection Laws, and any implementations of such\r\n" + 
				"Laws, applicable to its performance under this Agreement. The Parties acknowledge and agree that they will\r\n" + 
				"consider in good faith implementing any codes of practice and best practice guidance issued by relevant\r\n" + 
				"authorities as they apply to applicable country specific data protection Laws or their implementations.\r\n" );
				
				Paragraph personal_information = new Paragraph();
				personal_information.add(
				"7.6.2 Without limiting the generality of the foregoing, if Licensor is collecting or furnishing\r\n" +
				"Personal Information to Buyer or if Licensor is processing, storing or transferring Personal Information on behalf\r\n" + 
				"of Buyer, then Licensor and Buyer and/or their Affiliate(s), as applicable, will agree to supplemental privacy and\r\n" + 
				"security terms consistent with applicable Law, and if the Personal Information is regarding individuals in the\r\n" + 
				"European Economic Area, Licensor and Buyer agree to be bound by the attached Data Processing Addendum or\r\n" + 
				"other terms and conditions agreed upon by Buyer and Licensor that reflect their respective legal obligations with\r\n" + 
				"respect to Personal Information and any applicable data transfer mechanisms. For purposes of this Agreement,\r\n" + 
				"“Personal Information” means any information relating to an identified or identifiable natural person; an\r\n" + 
				"identifiable person is one who can be identified, directly or indirectly, in particular by reference to an identifier\r\n" + 
				"such as a name, an identification number, location data, an online identifier or to one or more factors specific to\r\n" + 
				"his or her physical, physiological, mental, economic, cultural or social identity or any data, data element or\r\n" + 
				"information that is subject to breach notification, data security obligations or other data protection Laws. For the\r\n" + 
				"avoidance of doubt, no Personal Information should be processed or transferred under this Agreement without\r\n" + 
				"Privacy and Security Terms necessary for compliance with applicable Law.\r\n" );
				
				Paragraph remedy = new Paragraph();
				remedy.add(
				"7.7 Remedies. Each Party agrees that in the event of a breach or threatened breach of this Section 7,\r\n" + 
				"the non-breaching Party will be entitled to injunctive relief against the breaching Party in addition to any other\r\n" + 
				"remedies to which the non-breaching Party may be entitled. Either Party may terminate this Agreement\r\n" + 
				"immediately upon written notice to the other Party if the other Party breaches any of the provisions set forth in this Section 7.\r\n" );
				
				Header limitations = new Header();
				limitations.add("8. Limitations of Liability.\r\n" );
				
				Paragraph Disclaimer = new Paragraph();
				Disclaimer.add(
				"8.1 Disclaimer; General Cap. SUBJECT TO SECTIONS 8.2, 8.3 AND 8.4, IN NO EVENT WILL\r\n" + 
				"(a) EITHER PARTY BE LIABLE TO THE OTHER PARTY FOR ANY INDIRECT, SPECIAL, PUNITIVE,\r\n" + 
				"INCIDENTAL OR CONSEQUENTIAL DAMAGES ARISING OUT OF OR IN CONNECTION WITH THIS\r\n" + 
				"AGREEMENT, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGES, AND (b) EITHER\r\n" + 
				"PARTY’S AGGREGATE LIABILITY UNDER THIS AGREEMENT, WHETHER SUCH DAMAGES ARE\r\n" + 
				"BASED IN CONTRACT, TORT OR OTHER LEGAL THEORY, EXCEED THE GREATER OF (i) IN THE\r\n" + 
				"CASE OF A SUBSCRIPTION WITH ENTITLEMENT PRICING, THE FEES AND OTHER AMOUNTS PAID\r\n" + 
				"AND REQUIRED TO BE PAID BY BUYER FOR THE SUBSCRIPTION, AND, IN THE CASE OF A\r\n" + 
				"SUBSCRIPTION WITH METERED PRICING, THE FEES AND OTHER AMOUNTS PAID AND REQUIRED\r\n" + 
				"TO BE PAID UNDER THIS AGREEMENT IN THE 12 MONTHS PRECEDING THE EVENT GIVING RISE\r\n" + 
				"TO THE DAMAGES, OR (ii) $500,000.\r\n" );
				
				Paragraph Exception = new Paragraph();
				Exception.add(
				"8.2 Exception for Gross Negligence, Willful Misconduct or Fraud. THE EXCLUSIONS OF\r\n" + 
				"AND LIMITATIONS ON LIABILITY SET FORTH IN SECTION 8.1(a) AND (b) WILL NOT APPLY TO A\r\n" + 
				"PARTY’S GROSS NEGLIGENCE, WILLFUL MISCONDUCT, OR FRAUD.\r\n" );
				
				Paragraph Indemnification = new Paragraph();
				Indemnification.add(
				"8.3 Exception for Certain Indemnification Obligations. THE EXCLUSIONS OF AND\r\n" + 
				"LIMITATIONS ON LIABILITY SET FORTH IN SECTIONS 8.1(a) AND (b) WILL NOT APPLY TO ANY\r\n" + 
				"COSTS OF DEFENSE AND ANY AMOUNTS AWARDED AGAINST THE INDEMNIFIED PARTY BY A\r\n" + 
				"COURT OF COMPETENT JURISDICTION OR AGREED UPON PURSUANT TO SETTLEMENT\r\n" + 
				"AGREEMENT THAT ARE SUBJECT TO SUCH PARTY’S INDEMNIFICATION AND DEFENSE\r\n" + 
				"OBLIGATIONS UNDER THIS AGREEMENT.\r\n" );
				
				Header Breach = new Header();
				Breach.add("8.4 Special Cap for Security Breach.");
				Paragraph Breach1 = new Paragraph();
				Breach1.add("8.4.1 FOR SAAS SUBSCRIPTIONS, THE EXCLUSIONS OF AND LIMITATIONS ON\r\n" + 
				"LIABILITY SET FORTH IN SECTIONS 8.1(a) AND (b) WILL NOT APPLY TO, AND INSTEAD SECTION\r\n" + 
				"8.4.2 WILL APPLY TO: (a) BUYER’S COSTS OF INVESTIGATION, NOTIFICATION, REMEDIATION\r\n" + 
				"AND MITIGATION RESULTING FROM ANY UNAUTHORIZED ACCESS, USE OR DISCLOSURE OF\r\n" + 
				"BUYER DATA RESULTING FROM BREACH OF LICENSOR’S OBLIGATIONS UNDER ANY PRIVACY\r\n" + 
				"AND SECURITY TERMS, INCLUDING NOTICE OF BREACH TO AFFECTED INDIVIDUALS,\r\n" + 
				"INDUSTRY SELF-REGULATORY AGENCIES, GOVERNMENT AUTHORITIES AND THE PUBLIC, AND\r\n" + 
				"CREDIT AND IDENTITY THEFT MONITORING SERVICES FOR AFFECTED INDIVIDUALS AND\r\n" + 
				"LICENSOR’S OBLIGATIONS WITH RESPECT THERETO PURSUANT TO SECTION 9.5; AND (b) ANY\r\n" + 
				"LIABILITIES ARISING FROM CLAIMS BROUGHT BY THIRD PARTIES AGAINST BUYER ARISING\r\n" + 
				"FROM ANY UNAUTHORIZED ACCESS, USE OR DISCLOSURE OF BUYER DATA RESULTING FROM\r\n" + 
				"BREACH OF LICENSOR’S OBLIGATIONS UNDER ANY PRIVACY AND SECURITY TERMS,\r\n" + 
				"INCLUDING OUT-OF-POCKET COSTS OF DEFENSE AND ANY AMOUNTS AWARDED AGAINST\r\n" + 
				"BUYER BY A COURT OF COMPETENT JURISDICTION OR AGREED UPON PURSUANT TO A\r\n" + 
				"SETTLEMENT AGREEMENT.\r\n" );
				
				Paragraph Breach2 = new Paragraph();
				Breach2.add(
				"8.4.2 FOR SAAS SUBSCRIPTIONS, LICENSOR’S AGGREGATE LIABILITY UNDER\r\n" + 
				"THIS AGREEMENT FOR ANY UNAUTHORIZED ACCESS, USE OR DISCLOSURE OF BUYER DATA\r\n" + 
				"RESULTING FROM BREACH OF LICENSOR’S OBLIGATIONS UNDER ANY PRIVACY AND SECURITY\r\n" + 
				"TERMS, INCLUDING BUYER’S COSTS SET FORTH IN SECTION 8.4.1 AND LICENSOR’S\r\n" + 
				"INDEMNIFICATION AND DEFENSE OBLIGATIONS PURSUANT TO SECTION 9.1(b) AND ITS\r\n" + 
				"OBLIGATIONS PURSUANT TO SECTION 9.5, WHETHER SUCH DAMAGES ARE BASED IN\r\n" + 
				"CONTRACT, TORT OR OTHER LEGAL THEORY, WILL NOT EXCEED (IN LIEU OF AND NOT IN\r\n" + 
				"ADDITION TO THE AMOUNT SET FORTH IN SECTION 8.1) THE GREATER OF (i) IN THE CASE OF A\r\n" + 
				"Standard Contract for AWS Marketplace v2 (2019-04-24)\r\n" + 
				"CONFIDENTIAL Page 9 of 23\r\n" + 
				"SUBSCRIPTION WITH ENTITLEMENT PRICING, FIVE TIMES THE FEES AND OTHER AMOUNTS\r\n" + 
				"PAID AND REQUIRED TO BE PAID BY BUYER FOR THE SUBSCRIPTION, AND, IN THE CASE OF A\r\n" + 
				"SUBSCRIPTION WITH METERED PRICING, FIVE TIMES THE FEES AND OTHER AMOUNTS PAID\r\n" + 
				"AND REQUIRED TO BE PAID UNDER THIS AGREEMENT IN THE 12 MONTHS PRECEDING THE\r\n" + 
				"EVENT GIVING RISE TO THE DAMAGES OR (ii) $2 MILLION.\r\n" );
				

				Header Indemnification1 = new Header();
				Indemnification1.add("9. Indemnification.");
				Paragraph indemnify = new Paragraph();
				indemnify.add(
				"9.1 Licensor Indemnity. Licensor will, at its expense, indemnify, defend and hold harmless Buyer\r\n" + 
				"and its Affiliates and their respective officers, directors, employees, agents and representatives (collectively\r\n" + 
				"“Buyer Indemnified Parties”) from and against any and all claims, actions, proceedings and suits brought by a\r\n" + 
				"third party, and any and all liabilities, losses, damages, settlements, penalties, fines, costs and expenses (including\r\n" + 
				"reasonable attorneys’ fees) (“Claims”), to the extent arising out of or relating to an allegation of any of the\r\n" + 
				"following: (a) infringement, misappropriation or violation of any Proprietary Rights by the Licensed Materials or\r\n" + 
				"Buyer’s use thereof as permitted under this Agreement; and (b) any unauthorized access, use or disclosure of\r\n" + 
				"Buyer Data resulting from breach of Licensor’s obligations under any Privacy and Security Terms.\r\n" );
				
				Paragraph  Buyer_Indemnity = new Paragraph();
				Buyer_Indemnity.add(
				"9.2 Buyer Indemnity. Buyer will, at its expense, indemnify, defend and hold harmless Licensor and\r\n" + 
				"its Affiliates and their respective officers, directors, employees, agents and representatives (collectively “Licensor\r\n" + 
				"Indemnified Parties”) from and against any and all claims, actions, proceedings and suits brought by a third\r\n" + 
				"party, and any and all liabilities, losses, damages, settlements, penalties, fines, costs and expenses (including\r\n" + 
				"reasonable attorneys’ fees) (“Claims”) to the extent arising out of or relating to an allegation of any of the\r\n" + 
				"following: (a) infringement, misappropriation or violation of any Proprietary Rights by the Buyer Materials or\r\n" + 
				"Buyer Data or Licensor’s use thereof as permitted under this Agreement; and (b) any unauthorized or unlawful\r\n" + 
				"receipt, processing, transmission or storage of Buyer Data by Licensor in the performance of its obligations as\r\n" + 
				"permitted under this Agreement resulting from breach of Buyer’s obligations under Section 7.2.2.\r\n" );
				
				Paragraph Process = new Paragraph();
				Process.add("9.3 Process. The party(ies) seeking indemnification pursuant to this Section 9 (each, an\r\n" + 
				"“Indemnified Party” and collectively, the “Indemnified Parties”) will give the other Party (the “Indemnifying\r\n" + 
				"Party”) prompt notice of each Claim for which it seeks indemnification, provided that failure or delay in\r\n" + 
				"providing such notice will not release the Indemnifying Party from any obligations hereunder except to the extent\r\n" + 
				"that the Indemnifying Party is prejudiced by such failure. The Indemnified Parties will give the Indemnifying\r\n" + 
				"Party their reasonable cooperation in the defense of each Claim for which indemnity is sought, at the\r\n" + 
				"Indemnifying Party’s expense. The Indemnifying Party will keep the Indemnified Parties informed of the status\r\n" + 
				"of each Claim. An Indemnified Party may participate in the defense at its own expense. The Indemnifying Party\r\n" + 
				"will control the defense or settlement of the Claim, provided that the Indemnifying Party, without the Indemnified\r\n" + 
				"Parties’ prior written consent: (a) will not enter into any settlement that; (i) includes any admission of guilt or\r\n" + 
				"wrongdoing by any Indemnified Party; (ii) imposes any financial obligations on any Indemnified Party that\r\n" + 
				"Indemnified Party is not obligated to pay under this Section 9; (iii) imposes any non-monetary obligations on any\r\n" + 
				"Indemnified Party; and (iv) does not include a full and unconditional release of any Indemnified Parties; and (b)\r\n" + 
				"will not consent to the entry of judgment, except for a dismissal with prejudice of any Claim settled as described\r\n" + 
				"in (a). The Indemnifying Party will ensure that any settlement into which it enters for any Claim is made\r\n" + 
				"confidential, except where not permitted by applicable Law.\r\n" );
				
				Paragraph Infringement = new Paragraph();
				Infringement.add("9.4 Infringement Remedy. In addition to Licensor’s obligations under Section 9.1, if the Software\r\n" + 
				"or other Licensed Materials is held, or in Licensor’s opinion is likely to be held, to infringe, misappropriate or\r\n" + 
				"violate any Proprietary Rights, or, if based on any claimed infringement, misappropriation or violation of\r\n" + 
				"Proprietary Rights, an injunction is obtained, or in Licensor’s opinion an injunction is likely to be obtained, that\r\n" + 
				"would prohibit or interfere with Buyer’s use of the Licensed Materials under this Agreement, then Licensor will at\r\n" + 
				"its option and expense either: (a) procure for Buyer the right to continue using the affected Licensed Materials in\r\n" + 
				"accordance with the license granted under this Agreement; or (b) modify or replace the affected Licensed\r\n" + 
				"Materials so that the modified or replacement Licensed Materials are reasonably comparable in functionality,\r\n" + 
				"interoperability with other software and systems, and levels of security and performance and do not infringe,\r\n" + 
				"misappropriate or violate any third-party Proprietary Rights. If, in such circumstances, Licensor cannot not\r\n" + 
				"successfully accomplish any of the foregoing actions on a commercially reasonable basis, Licensor will notify\r\n" + 
				"Buyer and either Party may terminate the Subscription and this Agreement, in which case Licensor will refund to\r\n" + 
				"Buyer any fees prepaid to Licensor by Buyer prorated for the unused portion of the Subscription. For clarity,\r\n" + 
				"Licensor’s indemnification and defense obligations under this Section include infringement Claims based on use\r\n" + 
				"of the Licensed Materials by Buyer Indemnified Parties following an initial infringement Claim except that, if\r\n" + 
				"Licensor responds to an infringement Claim by accomplishing the solution in (b), Licensor will have no\r\n" + 
				"obligation to defend and indemnify Buyer for infringement Claims arising from Buyer’s use after the\r\n" + 
				"accomplishment of (b) of the infringing Licensed Materials for which Licensor provided modified or replacement\r\n" + 
				"Licensed Materials.\r\n");
				
				Paragraph unauthorized = new Paragraph();
				unauthorized.add("9.5 Security Breach Remedy. In the case of a SaaS Subscription, in addition to Licensor’s\r\n" + 
				"obligations under Section 9.1, if any unauthorized access, use or disclosure of any Buyer Data results from\r\n" + 
				"breach of Licensor’s obligations under any Privacy and Security Terms, Licensor will pay the reasonable and\r\n" + 
				"documented costs incurred by Buyer for investigation, notification, remediation and mitigation concerning such\r\n" + 
				"unauthorized access, use or disclosure of Buyer Data, including notice of breach to affected individuals, industry\r\n" + 
				"self-regulatory agencies, government authorities and the public, and credit and identity theft monitoring services\r\n" + 
				"for affected individuals.\r\n" );
				
				Header Limitations = new Header();
				Limitations.add("9.6 Limitations. "); 
				
					Paragraph liability = new Paragraph();
					liability.add("9.6.1 Licensor will have no liability or obligation under this Section 9 with respect to any\r\n" + 
				"infringement Claim to the extent attributable to any: (a) modifications to the Licensed Materials not provided by\r\n" + 
				"Licensor or its Personnel; (b) use of the Software in combination with third-party equipment or software not\r\n" + 
				"provided or made accessible by Licensor or not specifically referenced for use with the Licensed Materials by the\r\n" + 
				"Standard Contract Listing or Documentation; or (c) use of the Licensed Materials by Buyer in breach of this\r\n" + 
				"Agreement. Licensor’s liability under this Section 9 with respect to any infringement Claim that is attributable to\r\n" + 
				"use of the Software in combination with third-party equipment or software provided or made accessible by\r\n" + 
				"Licensor or specifically referenced by the Standard Contract Listing or Documentation is limited to Licensor’s\r\n" + 
				"proportional share of defense costs and indemnity liability based on the lesser of: (i) the value of the contribution\r\n" + 
				"of the Licensed Materials to the total value of the actual or allegedly infringing combination; or (ii) the relative\r\n" + 
				"contribution of the Licensed Materials to the actual or allegedly infringed claims (e.g., the Licensed Materials are\r\n" + 
				"alleged to satisfy one limitation of a claim with four separate limitations and Licensor would be responsible for a\r\n" + 
				"25% share of the defense and indemnity obligations).\r\n");
					
				Paragraph Claim = new Paragraph();
				Claim.add("9.6.2 Buyer will have no liability or obligation under this Section 9 with respect to any\r\n" + 
				"infringement Claim to the extent attributable to any: (a) modifications to the Buyer Materials or Buyer Data not\r\n" + 
				"provided by Buyer or its Personnel; or (b) use of the Buyer Materials or Buyer Data by Licensor in breach of this\r\n" + 
				"Agreement.\r\n" );
				
				Paragraph infringement = new Paragraph();
				infringement.add("9.6.3 This Section 9 states the entire liability of Licensor with respect to infringement,\r\n" + 
				"misappropriation or violation of Proprietary Rights of third parties by any Licensed Materials or any part thereof\r\n" + 
				"or by any use thereof by Buyer, and this Section 9 states the entire liability of Buyer with respect to infringement,\r\n" + 
				"misappropriation or violation of Proprietary Rights of third parties by any Buyer Materials, Buyer Data or any\r\n" + 
				"part thereof or by any use, receipt, storage or processing thereof by Licensor.\r\n" );
				
				Paragraph indemnities = new Paragraph();
				indemnities.add("9.7 Not Limiting. The foregoing indemnities will not be limited in any manner whatsoever by any\r\n" + 
				"required or other insurance coverage maintained by a Party.\r\n" );
						
				Header Termination= new Header();
				Termination.add("10. Term and Termination.\r\n" );
				
				Paragraph conclusion = new Paragraph();
				conclusion.add("10.1 Term. This Agreement will continue in full force and effect until conclusion of the Subscription,\r\n" + 
				"unless terminated earlier by either Party as provided by this Agreement.\r\n");
				
				Paragraph Convenience = new Paragraph();
				Convenience.add("10.2 Termination for Convenience. Buyer may terminate the Subscription or this Agreement\r\n" + 
				"without cause at any time upon notice to Licensor or using the termination or cancellation functionality available\r\n" + 
				"through the AWS Services. If a Subscription with Metered Pricing, Buyer will pay for all Software usage up to\r\n" + 
				"the time of termination. If a Subscription with Entitlement Pricing, Buyer will not be entitled to refund of fees\r\n" + 
				"nor relieved of any future payment obligations for any unused portion of the Subscription.\r\n" );
				
				Paragraph breaches = new Paragraph();
				breaches.add("10.3 Termination for Cause. Either Party may terminate the Subscription or this Agreement if the\r\n" + 
				"other Party materially breaches this Agreement and does not cure the breach within 30 days following its receipt\r\n" + 
				"of written notice of the breach from the non-breaching Party. In the case of a SaaS Subscription, termination by\r\n" + 
				"Licensor pursuant to this Section does not prejudice Buyer’s right, and Licensor’s obligation, to extract or assist\r\n" + 
				"with the retrieval or deletion of Buyer Data as set forth in Section 10.4.2 following such termination.\r\n" + 
				"10.4 Effect of Termination.\r\n");
				
				Paragraph expiration = new Paragraph();
				expiration.add("10.4.1 Upon termination or expiration of the Subscription or this Agreement, Buyer’s right to\r\n" + 
				"use the Software licensed under such Subscription will terminate, and Buyer’s access to the Software and Service\r\n" + 
				"provided under such Subscription may be disabled and discontinued. Termination or expiration of any\r\n" + 
				"Subscription purchased by Buyer from Licensor will not terminate or modify any other Subscription purchased by\r\n" + 
				"Buyer from Licensor.\r\n");
				
				Paragraph request = new Paragraph();
				request.add("10.4.2 Within 30 days following termination or expiration of any SaaS Subscription for any\r\n" + 
				"reason and on Buyer’s written request at any time before termination or expiration, Licensor will extract from the\r\n" + 
				"SaaS Service and return to Buyer all Buyer Data, or if Buyer is able directly to retrieve or delete Buyer Data from\r\n" + 
				"the SaaS Service, then for a period of 30 days following termination or expiration of this Agreement for any\r\n" + 
				"reason, Buyer may retrieve or delete Buyer Data itself with support from Licensor as reasonably requested by\r\n" + 
				"Buyer. If Buyer retrieves or deletes Buyer Data itself, Licensor will assist Buyer, as reasonably requested by\r\n" + 
				"Buyer, in validating whether the retrieval or deletion was successful. Buyer Data must be provided or extractable\r\n" + 
				"in a then-current, standard nonproprietary format. Notwithstanding anything herein to the contrary, Licensor’s\r\n" + 
				"duty to return or enable Buyer’s retrieval or deletion of the Buyer Data pursuant to this Section 10.4.2 will not be\r\n" + 
				"discharged due to the occurrence of any Force Majeure event. Following delivery to Buyer of the Buyer Data and\r\n" + 
				"Buyer’s confirmation thereof, or Buyer’s retrieval or deletion of Buyer Data and Licensor’s validation thereof,\r\n" + 
				"Licensor will permanently delete and remove Buyer Data (if any) from its electronic and hard copy records and\r\n" + 
				"will, upon Buyer’s request, certify to such deletion and removal to Buyer in writing. If Licensor is not able to\r\n" + 
				"delete any portion of the Buyer Data or Buyer Confidential Information, it will remain subject to the\r\n" + 
				"confidentiality, privacy and data security terms of this Agreement.\r\n" );
				
				Paragraph Proprietary = new Paragraph();
				Proprietary.add("10.4.3 Sections 4 (Proprietary Rights), 6 (Confidentiality), 7.2.1 (Buyer Data and Buyer\r\n" + 
				"Materials), 8 (Limitations of Liability), 9 (Indemnification), 10.4 (Effect of Termination), 11 (Insurance),\r\n" + 
				"12(General) and 13 (Definitions) and any perpetual license granted under this Agreement, together with all other\r\n" + 
				"provisions of this Agreement that may reasonably be interpreted or construed as surviving expiration or\r\n" + 
				"termination, will survive the expiration or termination of this Agreement for any reason; but the nonuse and\r\n" + 
				"nondisclosure obligations of Section 6 will expire five years following the expiration or termination of this\r\n" + 
				"Agreement, except with respect to, and for as long as, any Confidential Information constitutes a trade secret.\r\n" );
				
				Header Insurance = new Header();
				Insurance.add("11. Insurance.\r\n" );
				
				Paragraph Coverages = new Paragraph();
				Coverages.add("11.1 Coverages. Each Party will obtain and maintain appropriate insurance necessary for\r\n" + 
				"implementing and performing under this Agreement in accordance with applicable Law and in accordance with\r\n" + 
				"the requirements of this Section 11. Subject to Licensor’s right to self-insure as described below, Licensor will at\r\n" + 
				"its own cost and expense, acquire and continuously maintain the following insurance coverage during the term of\r\n" + 
				"this Agreement and for one year after:\r\n" );
				
				Paragraph Commercial = new Paragraph();
				Commercial.add
				("11.1.1 Commercial General Liability insurance, including all major coverage categories,\r\n" + 
				"including premises-operations, property damage, products/completed operations, contractual liability, personal\r\n" + 
				"and advertising injury with limits of $1,000,000 per occurrence and $2,000,000 general aggregate, and $5,000,000\r\n" + 
				"products/completed operations aggregate;\r\n");
					
				Paragraph Professional = new Paragraph();
				Professional.add
				("11.1.2 Professional Liability insurance, covering liabilities for financial loss resulting or arising \r\n" +
				"from acts, errors or omissions in rendering Services in connection with this Agreement including acts, errors or \r\n" +
				"omissions in rendering computer or information technology Services, proprietary rights infringement, data\r\n" +
				"damage/destruction/corruption, failure to protect privacy, unauthorized access, unauthorized use, virus \r\n" +
				"transmission and denial of service from network security failures with a minimum limit of $2,000,000 each claim\r\n" +
				"and annual aggregate; \r\n" );
						
				Paragraph Cyber = new Paragraph();
				Cyber.add
				("11.1.3 If a SaaS Subscription, Cyber Liability or Technology Errors and Omissions, with limits\r\n" +
				"of $2,000,000 each claim and annual aggregate, providing for protection against liability for: (a) system attacks;\r\n" +
				"(b) denial or loss of service attacks; (c) spread of malicious software code; (d) unauthorized access and use of\r\n" +
				"computer systems; (e) liability arising from loss or disclosure of personal or corporate confidential data; (f) cyber\r\n" +
				"extortion; (g) breach response and management coverage; (h) business interruption; and (i) invasion of privacy;\r\n" );
				
				Paragraph Coverage = new Paragraph();
				Coverage.add(
				"11.1.4 If a SaaS Subscription, Computer Crime Insurance with limits of $1,000,000 and\r\n" +
				"Employee Theft/Buyer Insurance Coverage with limits of $500,000.\r\n" );
				
				Paragraph insurance = new Paragraph();
				insurance.add(
				"11.2 Umbrella Insurance; Self-Insurance. The limits of insurance may be satisfied by any\r\n" +
				"combination of primary and umbrella/excess insurance. In addition, either Party may satisfy its insurance\r\n" +
				"obligations specified in this Agreement through a self-insured retention program. Upon request by Buyer,\r\n" +
				"Licensor will provide evidence of Licensor’s self-insurance program in a formal declaration (on Licensor’s\r\n" +
				"letterhead, if available) that declares Licensor is self-insured for the type and amount of coverage as described in\r\n" +
				"Section 11.1. Licensor’s declaration may be in the form of a corporate resolution or a certified statement from a\r\n" +
				"corporate officer or an authorized principal of Licensor. The declaration also must identify which required\r\n" +
				"coverages are self-insured and which are commercially insured.\r\n" );
				
				Paragraph evidencing = new Paragraph();
				evidencing.add(
				"11.3 Certificates and Other Requirements. Prior to execution of this Agreement and annually\r\n" +
				"thereafter during the term, Buyer may request that Licensor furnish to Buyer a certificate of insurance evidencing\r\n" +
				"the coverages set forth above. Licensor’s Commercial General Liability and any umbrella insurance relied upon\r\n" +
				"to meet the obligations in this Section will be primary and non-contributory coverage and the policies will not\r\n" +
				"contain any intra-insured exclusions as between insured persons or organizations. Licensor’s Commercial\r\n" +
				"General Liability policy will provide a waiver of subrogation in favor of Buyer and its Affiliates. The stipulated\r\n" +
				"limits of coverage above will not be construed as a limitation of any potential liability to Buyer, and failure to\r\n" +
				"request evidence of this insurance will not be construed as a waiver of Licensor’s obligation to provide the\r\n" +
				"insurance coverage specified.\r\n" );
				
				Header  General = new Header();
				General.add("12. General.");
				
				Paragraph Applicable = new Paragraph();
				Applicable.add(
				"12.1 Applicable Law. This Agreement will be governed and interpreted under the laws of the State of\r\n" +
				"New York, excluding its principles of conflict of laws. The Parties agree that the exclusive forum for any action\r\n" +
				"or proceeding will be in New York County, New York, and the Parties consent to the jurisdiction of the state and\r\n" +
				"federal courts located in New York County, New York. The Parties agree that the United Nations Convention on\r\n" +
				"Contracts for the International Sale of Goods does not apply to this Agreement.\r\n" );
				
				Paragraph Assignment = new Paragraph();
				Assignment.add(
				"12.2 Assignment. Neither Party may assign or transfer this Agreement or any rights or delegate any\r\n" +
				"duties herein without the prior written consent of the other Party, which will not be reasonably withheld, delayed\r\n" +
				"or conditioned. Notwithstanding the foregoing, and without gaining the other Party’s written consent, either Party\r\n" +
				"may assign this Agreement, in whole or part, and delegate its obligations to its Affiliates or to any entity acquiring\r\n" +
				"all or substantially all of its assets related to the Standard Contract Listing or the assigning Party’s entire business,\r\n" +
				"whether by sale of assets, sale of stock, merger or otherwise. Any attempted assignment, transfer or delegation in\r\n" +
				"contravention of this Section will be null and void. This Agreement will inure to the benefit of the Parties hereto\r\n" +
				"and their permitted successors and assigns.\r\n");
				
				Paragraph Divestiture = new Paragraph();
				Divestiture.add(
				"12.3 Divestiture. If Buyer divests a portion of its business to one or more organizations that are not\r\n" +
				"Affiliates of Buyer, or if an entity ceases to be an Affiliate of Buyer (such divested business unit or such entity, a\r\n" +
				"“Divested Affiliate”), Licensor agrees to allow such Divested Affiliate to continue to use the Software, and Buyer\r\n" +
				"may elect that (a) such Divested Affiliate continue, as if it were a Buyer Affiliate, to use the Software under\r\n" +
				"Buyer’s AWS Marketplace account if an AMI Subscription and under Buyer’s account with Licensor if a SaaS\r\n" +
				"Subscription for the remainder of the Subscription, or (b) such Divested Affiliate may obtain its own\r\n" +
				"Subscription to the Software for a period of 90 days after the effective date of such divestiture under the same\r\n" +
				"terms and conditions as this Agreement and the same pricing as set forth in the Standard Contract Listing. Use by\r\n" +
				"a Divested Affiliate after the conclusion of the Subscription or 90 day period, as applicable, will require a\r\n" +
				"separately purchased subscription from Licensor through an AWS Marketplace account of that Divested Affiliate\r\n" +
				"or its then-current Affiliates.\r\n");
				
				Paragraph amendment = new Paragraph();
				amendment.add(
				"12.4 Entire Agreement. This Agreement constitutes the entire agreement between the Parties relating\r\n" +
				"to the subject matter hereof, and there are no other representations, understandings or agreements between the\r\n" +
				"Parties relating to the subject matter hereof. This Agreement is solely between Buyer and Licensor. Neither\r\n" +
				"Amazon Web Services, Inc. nor any of its Affiliates are a party to this Agreement and none of them will have any\r\n" +
				"liability or obligations hereunder. The terms and conditions of this Agreement will not be changed, amended,\r\n" +
				"modified or waived unless such change, amendment, modification or waiver is in writing and signed by\r\n" +
				"authorized representatives of the Parties. NEITHER PARTY WILL BE BOUND BY, AND EACH\r\n" +
				"SPECIFICALLY OBJECTS TO, ANY PROVISION THAT IS DIFFERENT FROM OR IN ADDITION TO\r\n" +
				"THIS AGREEMENT (WHETHER PROFFERED ORALLY OR IN ANY QUOTATION, PURCHASE ORDER,\r\n" +
				"INVOICE, SHIPPING DOCUMENT, ONLINE TERMS AND CONDITIONS, ACCEPTANCE,\r\n" +
				"CONFIRMATION, CORRESPONDENCE, OR OTHERWISE), UNLESS SUCH PROVISION IS\r\n" +
				"SPECIFICALLY AGREED TO IN A WRITING SIGNED BY BOTH PARTIES.\r\n" );
				
				Paragraph Majeure = new Paragraph();
				Majeure.add(
				"12.5 Force Majeure. Neither Party will be liable hereunder for any failure or delay in the\r\n" +
				"performance of its obligations in whole or in part, on account of riots, fire, flood, earthquake, explosion,\r\n" +
				"epidemics, war, strike or labor disputes (not involving the Party claiming force majeure), embargo, civil or\r\n" +
				"military authority, act of God, governmental action or other causes beyond its reasonable control and without the\r\n" +
				"fault or negligence of such Party or its Personnel and such failure or delay could not have been prevented or\r\n" +
				"circumvented by the non-performing Party through the use of alternate sourcing, workaround plans or other\r\n" +
				"reasonable precautions, including, in the case of a SaaS Service, Licensor’s Business Continuity Plan, as required\r\n" +
				"under this Agreement (a “Force Majeure Event”). A Force Majeure Event will not excuse or suspend Licensor’s\r\n" +
				"obligation to invoke and follow its Business Continuity Plan in a timely fashion, and to the extent that such\r\n" +
				"Business Continuity Plan was designed to cover the specific force majeure, or events caused by the Force Majeure\r\n" +
				"Event, the foregoing will excuse Licensor’s performance under this Agreement only for the period of time from\r\n" +
				"the occurrence of the Force Majeure Event until Licensor invokes its Business Continuity Plan. If a Force\r\n" +
				"Majeure Event continues for more than 14 days for any Subscription with Entitlement Pricing, Buyer may cancel\r\n" +
				"the unperformed portion of the Subscription and receive a pro rata refund of any fees prepaid by Buyer to\r\n" +
				"Licensor for such unperformed portion.\r\n" );
				
				Paragraph Commerce = new Paragraph();
				Commerce.add(
				"12.6 Export Laws. Each Party will comply with all applicable customs and export control laws and\r\n" +
				"regulations of the United States and/or such other country, in the case of Buyer, where Buyer or its Users use the\r\n" +
				"Software or Services, and in the case of Licensor, where Licensor provides the Software or Services. Each Party\r\n"+
				"certifies that it and its Personnel are not on any of the relevant U.S. Government Lists of prohibited persons,\r\n" +
				"including but not limited to the Treasury Department’s List of Specially Designated Nationals and the Commerce\r\n" +
				"Department’s list of Denied Persons. Neither Party will export, re-export, ship, or otherwise transfer the Licensed\r\n" +
				"Materials, Services or Buyer Data to any country subject to an embargo or other sanction by the United States.\r\n" );
				
				Paragraph commercial = new Paragraph();
				commercial.add(
				"12.7 Government Rights. As defined in FARS §2.101, the Software and Documentation are\r\n" +
				"“commercial items” and according to DFARS §252.227 and 7014(a)(1) and (5) are deemed to be “commercial\r\n" +
				"computer software” and “commercial computer software documentation”. Consistent with FARS §12.212 and\r\n" +
				"DFARS §227.7202, any use, modification, reproduction, release, performance, display or discourse of such\r\n" +
				"commercial software or commercial software documentation by the U.S. government will be governed solely by\r\n" +
				"the terms of this Agreement and will be prohibited except to the extent expressly permitted by the terms of this\r\n" +
				"Agreement.\r\n");
				
				Paragraph Headings = new Paragraph();
				Headings.add(
				"12.8 Headings. The headings throughout this Agreement are for reference purposes only, and the\r\n" +
				"words contained therein will in no way be held to explain, modify, amplify or aid in the interpretation,\r\n" +
				"construction or meaning of the provisions of this Agreement.\r\n" );
				
				Paragraph Beneficiaries = new Paragraph();
				Beneficiaries.add
				("12.9 No Third-Party Beneficiaries. Except as specified in Section 9 with respect to Buyer\r\n" +
				"Indemnified Parties and Licensor Indemnified Parties, nothing express or implied in this Agreement is intended to\r\n" +
				"confer, nor will anything herein confer, upon any person other than the Parties and the respective successors or\r\n" +
				"assigns of the Parties, any rights, remedies, obligations or liabilities whatsoever.\r\n");
				
				Paragraph Notices = new Paragraph();
				Notices.add(
				"12.10 Notices. To be effective, notice under this Agreement must be given in writing. Each Party\r\n" +
				"consents to receiving electronic communications and notifications from the other Party in connection with this\r\n" +
				"Agreement. Each Party agrees that it may receive notices from the other Party regarding this Agreement: (a) by\r\n" +
				"email to the email address designated by such Party as a notice address for the Standard Contract; (b) by personal\r\n" +
				"delivery; (c) by registered or certified mail, return receipt requested; or (d) by nationally recognized courier\r\n" +
				"service. Notice will be deemed given upon written verification of receipt.\r\n" );
				
				Paragraph Nonwaiver = new Paragraph();
				Nonwaiver.add(
				"12.11 Nonwaiver. Any failure or delay by either Party to exercise or partially exercise any right, power\r\n" +
				"or privilege under this Agreement will not be deemed a waiver of any such right, power or privilege under this\r\n" +
				"Agreement. No waiver by either Party of a breach of any term, provision or condition of this Agreement by the\r\n" +
				"other Party will constitute a waiver of any succeeding breach of the same or any other provision hereof. No such\r\n" +
				"waiver will be valid unless executed in writing by the Party making the waiver.\r\n" );
				
				Paragraph Publicity = new Paragraph();
				Publicity.add(
				"12.12 Publicity. Neither Party will issue any publicity materials or press releases that refer to the other\r\n" +
				"Party or its Affiliates, or use any trade name, trademark, service mark or logo of the other Party or its Affiliates in\r\n" +
				"any advertising, promotions or otherwise, without the other Party’s prior written consent.\r\n" );
				
				Paragraph Relationship = new Paragraph();
				Relationship.add(
				"12.13 Relationship of Parties. The relationship of the Parties will be that of independent contractors,\r\n" +
				"and nothing contained in this Agreement will create or imply an agency relationship between Buyer and Licensor,\r\n" +
				"nor will this Agreement be deemed to constitute a joint venture or partnership or the relationship of employer and\r\n" +
				"employee between Buyer and Licensor. Each Party assumes sole and full responsibility for its acts and the acts of\r\n" +
				"its Personnel. Neither Party will have the authority to make commitments or enter into contracts on behalf of,\r\n" +
				"bind, or otherwise oblige the other Party.\r\n" );
				
				Paragraph Severability = new Paragraph();
				Severability.add(
				"12.14 Severability. If any term or condition of this Agreement is to any extent held invalid or\r\n" +
				"unenforceable by a court of competent jurisdiction, the remainder of this Agreement will not be affected thereby,\r\n" +
				"and each term and condition will be valid and enforceable to the fullest extent permitted by law.\r\n" );
				
				Paragraph omission = new Paragraph();
				omission.add(
				"12.15 Subcontracting. Licensor may use Subcontractors in its performance under this Agreement,\r\n" +
				"provided that: (a) Licensor remains responsible for all its duties and obligations hereunder and the use of any\r\n" +
				"Subcontractor will not relieve or reduce any liability of Licensor or cause any loss of warranty under this\r\n" +
				"Agreement; and (b) Licensor agrees to be directly liable for any act or omission by such Subcontractor to the\r\n" +
				"same degree as if the act or omission were performed by Licensor such that a breach by a Subcontractor of the\r\n" +
				"provisions of this Agreement will be deemed to be a breach by Licensor. The performance of any act or omission\r\n" +
				"under this Agreement by a Subcontractor for, by or through Licensor will be deemed the act or omission of\r\n" +
				"Licensor. Upon request, Licensor will identify to Buyer any Subcontractors performing under this Agreement,\r\n" +
				"including any that have access to Buyer Data, and such other information reasonably requested by Buyer about\r\n" +
				"such subcontracting.\r\n");
				
				Header definitition = new Header();
				definitition.add("13. Definitions.");
				
				Paragraph Affiliate = new Paragraph();
				Affiliate.add(
				"13.1 “Affiliate” means, with respect to a Party, any entity that directly, or indirectly through one or\r\n" +
				"more intermediaries, controls, or is controlled by, or is under common control with such Party.\r\n" );
				
				Paragraph provisioned = new Paragraph();
				provisioned.add(
				"13.2 “AMI” means a way that the Software offered under a Standard Contract Listing may be\r\n" +
				"provisioned to Buyer where the Software is delivered in a machine image using the Amazon Machine Image\r\n" +
				"functionality of AWS Services. Buyer deploys and runs the AMI Image containing the AMI Software under\r\n" +
				"Buyer’s own AWS Services account on AWS Services infrastructure.\r\n" );
				
				Paragraph functionality = new Paragraph();
				functionality.add(
            "13.3 “AMI Image” means the specific machine image in which AMI Software is delivered to Buyer\r\n" +
				"using the Amazon Machine Image functionality of AWS Services, including the AMI Software, the operating\r\n" +
				"system and all applications, services and information included therein.\r\n");
			
				
				Paragraph marketplace1 = new Paragraph();
				marketplace1.add(			
				 " 	13.4 “AWS Marketplace” means the software marketplace operated by Amazon Web S\r\n" +
				"located at https://aws.amazon.com/marketplace/ as it may be updated from time to time.\r\n");
					
				Paragraph computing = new Paragraph();
				computing.add(
				"13.5 “AWS Services” means the cloud computing services offered by Amazon Web Services, Inc. as\r\n" +
				"they may be updated from time to time.\r\n");
				
				Paragraph Confidential = new Paragraph();
				Confidential.add(
				"13.6 “Buyer Data” means all data, records, files, information or content, including text, sound, video,\r\n" +
				"images and software, that is (a) input or uploaded by Buyer or its Users to or collected, received, transmitted,\r\n" +
				"processed, or stored by Buyer or its Users using the Software or SaaS Service in connection with this Agreement,\r\n" +
				"or (b) derived from (a). Buyer Data is Confidential Information of Buyer.\r\n" );
				
				Paragraph furnished = new Paragraph();
				furnished.add(
				"13.7 “Buyer Materials” means any property, items or materials, including Buyer Data, furnished by\r\n" +
				"Buyer to Licensor for Licensor’s use in the performance of its obligations under this Agreement.\r\n" );
				
				Paragraph contractor = new Paragraph();
				contractor.add(
				"13.8 “Contractor” means any third party contractor of Buyer or other third party performing services\r\n" +
				"for Buyer, including outsourcing suppliers.\r\n" );
				
				Paragraph modifications = new Paragraph();
				modifications.add(
				"13.9 “Documentation” means the user guides, manuals, instructions, specifications, notes,\r\n" +
				"documentation, printed updates, “read-me” files, release notes and other materials related to the Software\r\n" +
				"(including all information included or incorporated by reference in the applicable Standard Contract Listing), its\r\n" +
				"use, operation or maintenance, together with all enhancements, modifications, derivative works, and amendments\r\n" +
				"to those documents, that Licensor publishes or provides under this Agreement.\r\n" );
				
				Paragraph Entitlement = new Paragraph();
				Entitlement.add(
				"13.10 “Entitlement Pricing” means any pricing model for AMI Software or SaaS Software\r\n" +
				"Subscriptions where Buyer purchases a quantity of usage upfront, include prepaid and installment payment\r\n" +
				"pricing models.\r\n" );
				
				Paragraph materials = new Paragraph();
				materials.add(
				"13.11 “Licensed Materials” means the Software, Documentation and any other items, materials or\r\n" +
				"deliverables that Licensor provides, or is obligated to provide, under this Agreement.\r\n" );
				
				Paragraph Pricing = new Paragraph();
				Pricing.add(
				"13.12 “Metered Pricing” means any pricing model for AMI Software or SaaS Software Subscriptions\r\n" +
				"where Buyer pays as it goes based on the quantity of its usage of the Software.\r\n" );
				
				Paragraph Personnel = new Paragraph();
				Personnel.add(
				"13.13 “Personnel” means a Party or its Affiliate’s directors, officers, employees, non-employee\r\n" +
				"workers, agents, auditors, consultants, contractors, subcontractors and any other person performing services on\r\n" +
				"behalf of such Party (but excludes the other Party and any of the foregoing of the other Party).\r\n" );
				
				Paragraph Addendum = new Paragraph();
				Addendum.add(
				"13.14 “Privacy and Security Terms” means Section 7.5, the attached Security Addendum and any\r\n" +
				"other terms and conditions regarding the privacy and security of data agreed upon by the parties that are a part of\r\n" +
				"this Agreement, whether in an addendum or amendment to this Standard Contract.\r\n" );
				
				Paragraph Proprietary1 = new Paragraph();
				Proprietary1.add(
				"13.15 “Proprietary Rights” means all intellectual property and proprietary rights throughout the world,\r\n" +
				"whether now known or hereinafter discovered or invented, including, without limitation, all: (a) patents and patent\r\n" +
				"applications; (b) copyrights and mask work rights; (c) trade secrets; (d) trademarks; (e) rights in data and\r\n" +
				"databases; and (f) analogous rights throughout the world.\r\n");
				
				Paragraph infrastructure = new Paragraph();
				infrastructure.add(
			"	13.16 “SaaS” means a way that the Software offered by Licensor under a Standard Contract Listing\r\n" +
				"may be provisioned to Buyer where the Software is delivered to Buyer on a software-as-a-service basis. The SaaS\r\n" +
				"Licensor deploys the hosted Software under Licensor’s account on the AWS Services infrastructure and is\r\n" +
				"responsible for granting Buyer access to and use of the Software and SaaS Service\r\n");
//				
//				Paragraph Commercial1 = new Paragraph();
//		       Commercial1.add.(
//		    		 "  13.17 SaaS Service means the SaaS Software as deployed and hosted by Licensor on the AWS\r\n" +
//		    		 "  Service infrastructure, any software and other technology provided or made accessible by Licensor that Buyer is\r\n" +
//		    		 "  required or has the option to use in order to access, receive and use the SaaS Software as hosted by Licensor,\r\n" +
//		    		 "  including any software or technology that Buyer is required or has the option to install, operate and use on\r\n" +
//		    		 "  Buyer’s systems for its use of the SaaS Software, and all related services, functions or responsibilities of Licensor\r\n" +
//		    		 "  inherent in, and necessary for, the proper performance of such software-as-a-service.\r\n" );
//				
				Paragraph vulnerabilities = new Paragraph();
				vulnerabilities.add(
				"13.19 “Software” means the computer software identified in the applicable Standard Contract Listing\r\n" +
				"and any other software, including any patches, bug fixes, corrections, remediation of security vulnerabilities,\r\n" +
				"updates, upgrades, modifications, enhancements, derivative works, new releases and new versions of the Software\r\n" +
				"that Licensor provides, or is obligated to provide, under this Agreement.\r\n" );
				
				Paragraph Contract = new Paragraph();
				Contract.add(
				"13.20 “Standard Contract Listing” means an offer by Licensor or a Reseller, as set forth in the detail\r\n" +
				"page on the AWS Marketplace, to license Software for a specific use capacity and provide Support Services\r\n" +
				"subject to this Standard Contract, including Licensor’s policies and procedures referenced or incorporated in the\r\n" +
				"detail page.\r\n" );
				
				Paragraph subcontractor = new Paragraph();
				subcontractor.add(
				"13.21 “Subcontractor” means any third party subcontractor or other third party to whom Licensor\r\n" +
				"delegates any of its duties and obligations under this Agreement.\r\n" );
				
				Paragraph Marketplace = new Paragraph();
				Marketplace.add(
				"13.22 “Subscription” means a subscription ordered by Buyer in the AWS Marketplace and fulfilled by\r\n" +
				"Licensor for the licensing and provision of AMI Software or SaaS Software listed in a Standard Contract Listing.\r\n" );
				
				Paragraph Support = new Paragraph();
				Support.add(
				"13.23 “Support Services” means the support and maintenance services for the Software that Licensor\r\n" +
				"provides, or is obligated to provide, as described in the Standard Contract Listing.\r\n");
				
				Paragraph System = new Paragraph();
				System.add(
				"13.24 “System Data” means data and data elements collected by the SaaS Software, SaaS Service or\r\n" +
				"Licensor’s computer systems regarding configuration, environment, usage, performance, vulnerabilities and\r\n" +
				"security of the SaaS Software or SaaS Service that may be used to generate logs, statistics and reports regarding\r\n" +
				"performance, availability, integrity and security of the SaaS Software.\r\n" );
				
				Paragraph employee = new Paragraph();
				employee.add(
				"13.25 “User” means an employee, non-employee worker or other member of Buyer or any of its\r\n" +
				"Affiliates’ workforces, Contractor of Buyer or any of its Affiliates or other person or software program or\r\n" +
				"computer systems authorized by Buyer or any of its Affiliates to access and use the Software as permitted under\r\n" +
				"this Agreement.\r\n");
				
				Paragraph Period = new Paragraph();
				Period.add(
				"13.26 “Warranty Period” means, in the case of SaaS Software with Entitlement Pricing for the term of\r\n" +
				"the Subscription and, in the case of AMI Software with Entitlement Pricing, 30 days after Buyer’s purchase of the\r\n" +
				"Subscription or the term of the Subscription, whichever is shorter\r\n"); 
				
				Label label1 = new Label("Security Addendum for");
				Label label2 = new Label("Standard Contract for AWS Marketplace");
				Label label3 = new Label("(Basic Security Requirements)");
				label1.getStyle().set("font-weight", "bold");
				label2.getStyle().set("font-weight", "bold");
				label3.getStyle().set("font-weight", "bold");
				label1.getStyle().set("align-self", "center");
				label2.getStyle().set("align-self", "center");
				label3.getStyle().set("align-self", "center");
				
				Paragraph Security = new Paragraph();
				Security.add(
				"This Security Addendum (this “Security Addendum”) is part of the Standard Contract for AWS Marketplace\r\n" +
				"(the “Standard Contract”) between Licensor and Buyer and governs the treatment of Confidential Information\r\n" +
				"of Buyer in the case of a SaaS Subscription. All capitalized terms used but not defined in this Addendum have the\r\n" +
				"meanings given to them in the Standard Contract.\r\n" );
				
				Paragraph accidental = new Paragraph();
				accidental.add(
				"1. Security Program. Licensor will, consistent with industry standard practices, implement and maintain a\r\n" +
			"	security program: (a) to maintain the security and confidentiality of Confidential Information; and (b) to protect\r\n" +
				"Confidential Information from known or reasonably anticipated threats or hazards to its security, availability and\r\n" +
				"integrity, including accidental loss, unauthorized use, access, alteration or disclosure. Licensor will safeguard\r\n" +
				"Buyer’s Confidential Information with at least the degree of care it uses to protect its own confidential\r\n" +
				"information of a like nature and no less than a reasonable degree of care. Without limitation, Licensor’s policies\r\n" +
				"will require, and the safeguards to be implemented by Licensor, will include at a minimum, but without limitation\r\n" +
				"to, the following:\r\n" );
					
					Paragraph administrative = new Paragraph();
					administrative.add(
				"1.1 appropriate administrative controls, such as communication of all applicable information security\r\n" +
				"policies, information security and confidentiality training, and assignment of unique access credentials (which\r\n" +
				"shall be revoked upon termination);\r\n");
				
				Paragraph business = new Paragraph();
				business.add(
				"1.2 controls to ensure the physical safety and security of all facilities (including third party locations)\r\n" +
				"where Confidential Information may be processed or stored, including, at a minimum, locked doors and keys/key\r\n" +
				"cards to access any facility and a business continuity plan that is regularly reviewed and updated;\r\n" );
				
				Paragraph prohibition = new Paragraph();
				prohibition.add(
				"1.3 controls to limit access to Licensor’s systems and Confidential Information, including a password\r\n" +
				"policy for all Personnel that access Confidential Information and a prohibition on the use of shared credentials for\r\n" +
				"users and/or systems; and\r\n" );
						
						Paragraph evaluation = new Paragraph();
						evaluation.add(
				"1.4 regular testing and evaluation of the effectiveness of the safeguards for the protection of\r\n" +
				"Confidential Information.\r\n" );
				
						Header duties = new Header();
						duties.add("2. Security Requirements. Without limiting Licensor’s duties and obligations under Section 1 of this\r\n" );
				Paragraph Addendum1 = new Paragraph();
				Addendum1.add(
				"Security Addendum, Licensor will comply with the following requirements:\r\n" );
				Header Access = new Header();
				Access.add("2.1 Licensor Systems; Access");
				
				Paragraph disclose = new Paragraph();
				disclose.add(
				"2.1.1 Licensor shall not and shall not permit a third party to access, use or disclose Confidential\r\n" +
				"Information except as specifically authorized in the Standard Contract or this Security Addendum.\r\n");
				
				Paragraph environment = new Paragraph();
				environment.add(
				"2.1.2 Licensor will safeguard Confidential Information in a controlled environment consistent\r\n" +
				"with industry standards.\r\n");
				
				Paragraph segregation = new Paragraph();
				segregation.add(
				"2.1.3 Licensor shall establish, maintain and enforce the security access principles of\r\n" +
				"“segregation of duties” and “least privilege” with respect to Confidential Information.\r\n" );
				
				Paragraph stored = new Paragraph();
				stored.add(
				"2.1.4 Licensor will maintain a list of systems where Confidential Information is processed and\r\n" +
				"stored and maintain a list of Personnel who have access to those systems.\r\n");
				
				Paragraph separation = new Paragraph();
				separation.add(
				"2.1.5 Licensor will have in place industry standard policies and processes to limit access to\r\n" +
				"Personal Information including: (i) a unique individual user-id will be used for each user that accesses\r\n" +
				"Confidential Information; (ii) any temporary password issued will be unique and must be changed upon first use;\r\n" +
				"(iii) no Confidential Information, nor a subset of Confidential Information (such as part of a user’s Social Security\r\n" +
				"Number), will be used in either the user-id or the initial temporary password; and (iv) it will establish a process to\r\n" +
				"ensure timely revocation of access when access is no longer allowed for an individual (e.g. separation, role\r\n" +
				"change).\r\n");
				
				Paragraph passwords = new Paragraph();
				passwords.add(
				"2.1.6 Licensor will have in place industry standard end user authentication processes including\r\n" +
				"that passwords will not be displayed, printed stored in clear text and will be required to be at least six characters,\r\n" +
				"case sensitive, different from user-ids and will be a combination of at least uppercase, lowercase and numerals.\r\n" +
				"The process for users to change their passwords will meet the following requirements: (i) passwords are not sent\r\n" +
				"in email (except for temporary/one-time use passwords); (ii) users receive a separate notification upon password\r\n" +
				"and/or profile changes such as an email or mail; and (iii) password resets require authentication of individual\r\n" +
				"identity.\r\n" );
				
				Paragraph authentication = new Paragraph();
				authentication.add(
				"2.1.7 Licensor will time out an authenticated session and require re-authentication should the\r\n" +
				"session expire. If using cookies for authenticated session management, the cookies must be marked as secure, and\r\n" +
				"any authentication material must be encrypted.\r\n" );
				
				Paragraph policies = new Paragraph();
				policies.add("2.1.8 Upon Buyer’s request, Licensor shall provide Buyer a copy of or online viewing access\r\n" +
				"to a summary of its policies, processes and administrative controls by which Confidential Information is used,\r\n" +
				"disclosed, stored, processed or otherwise transmitted or handled, and any material modifications to such policies,\r\n" +
				"processes and controls.\r\n");
				
                Header Personnel1 = new Header();
				Personnel1.add("2.2 Personnel.");
				
				Paragraph industry = new Paragraph();
				industry.add(
				"2.2.1 Access to Confidential Information will be restricted to authorized Personnel and\r\n" +
				"provided only on a need to know basis. Personnel having access to Confidential Information shall be bound by a\r\n" +
				"written agreement with Licensor with requirements and restrictions no less than those set forth herein. Each\r\n" +
				"Personnel must pass a background check consistent with industry standards before having access to Confidential\r\n" +
				"Information.\r\n" );
				
				Paragraph standards = new Paragraph();
				standards.add(
				"2.2.2 Licensor shall provide security awareness training to all Personnel authorized by\r\n" +
				"Licensor to have access to Confidential Information (“Authorized Personnel”). Such training shall be: (i)\r\n" +
				"consistent with industry standards; (ii) designed, at a minimum, to educate all such individuals on maintaining the\r\n" +
				"security, confidentiality and integrity of Personal Information consistent with this Security Addendum; and (iii) be\r\n" +
				"provided no less than annually.\r\n" );
				
				Paragraph accounts = new Paragraph();
				accounts.add(
				"2.2.3 Licensor shall have in place a process by which Authorized Personnel and other user\r\n" +
				"accounts are created and deleted in a secure and timely fashion.\r\n" +
				"2.3 Records and Risk Assessments.\r\n" );
				
				Paragraph enforce = new Paragraph();
				enforce.add(
				"2.3.1 Licensor agrees to maintain and enforce retention policies for any and all reports, logs,\r\n" +
				"audit trails and any other documentation that provides evidence of security, systems, and audit processes and\r\n" +
				"procedures in accordance with all applicable laws and regulations.\r\n" );
				
				Paragraph safeguards = new Paragraph();
				safeguards.add(
				"2.3.2 Licensor will conduct regular penetration testing or other appropriate security testing and\r\n" +
				"security assessments that verify its information security practices as to the use, handling and storage of\r\n" +
				"Confidential Information. Upon request from Buyer, Licensor will provide Buyer a copy of or online viewing\r\n" +
				"access to reports summarizing such testing and audits. If Licensor engages an independent third party to conduct\r\n" +
				"audits, upon request by Buyer, Licensor will provide to Buyer a copy of or online viewing access the audit reports\r\n" +
				"or certifications issued (or a summary of the audit reports if use or distribution of the reports is restricted by the\r\n" +
				"third party auditor) as a result of such audits. If Licensor conducts its own risk assessment, then Licensor will\r\n" +
				"provide Buyer with a copy of or online viewing access to its report of such assessment, including at a minimum a\r\n" +
				"summary of Licensor’s security program, including the safeguards, controls, policies and procedures with respect\r\n" +
				"to infrastructure, software, people, procedures, and data used to provide the SaaS Services (“Security Program”)\r\n" +
				"as verified against Licensor’s actual practices and any material vulnerabilities or issues identified in the audit.\r\n" +
				"Any such reports are Licensor’s Confidential Information.\r\n" );
				
				Paragraph manner = new Paragraph();
				manner.add(
				"2.3.3 Licensor shall remedy material issues identified from the testing and audits in a timely\r\n" +
				"manner.\r\n" );
				
				Paragraph Continuity = new Paragraph();
				Continuity.add(
				"2.4 Business Continuity. Licensor will establish and implement plans and risk controls, consistent\r\n" +
				"with industry standards, for continuity of its performance under this Agreement (“Business Continuity Plan”).\r\n" +
				"Licensor’s Business Continuity Plan will include safeguards to resume the SaaS Service, and recover and make\r\n" +
				"available Buyer Data, within a reasonable time after a security breach or any significant interruption or\r\n" +
				"impairment of operation or any loss, deletion, corruption or alteration of data. Licensor will review its Business\r\n" +
				"Continuity Plan on a regular basis and update it in response to changes within its company and industry standards.\r\n" +
				"Upon request, Licensor will provide Buyer a summary of its Business Continuity Plan that covers access and\r\n" +
				"processing of Buyer Confidential Information.\r\n" );
				
				Header Personal1 = new Header();
				Personal1.add("2.5 Personal Information.\r\n" );
				
				Paragraph acknowledges = new Paragraph();
				acknowledges.add(
				"2.5.1 Licensor understands and acknowledges that, to the extent that performance of its\r\n" +
				"obligations hereunder involves or necessitates the processing of Personal Information relating to individuals, it\r\n" +
				"shall act only on instructions and directions from Buyer as set out in the Standard Contract. Licensor shall comply\r\n" +
				"within a reasonable time frame (which shall in no event be longer than any time frame for compliance required by\r\n" +
				"law) with all such instructions and directions.\r\n");
				
				Paragraph practicable = new Paragraph();
				practicable.add(
				"2.5.2 Licensor shall as soon as reasonably practicable in the circumstances, and in any event\r\n" +
				"within three days of becoming aware of any data subject access request, serve notice on Buyer of any request\r\n" +
				"made by a data subject to access Personal Information processed by Licensor on behalf of Buyer and, if required\r\n" +
				"by Buyer, permit Buyer to handle such request and at all times cooperate with and assist Buyer to execute its\r\n" +
				"obligations under the law in relation to such data subject access requests.\r\n");
				
						Header relation = new Header();
						relation.add("3. Data Security Breach Notification.\r\n" );
				
				Paragraph Incidents = new Paragraph();
				Incidents.add(
				"3.1 Licensor will inform Buyer promptly upon discovery of any compromise, unauthorized access to,\r\n" +
				"alteration, loss, use or disclosure of any Confidential Information or any other breach of the confidentiality,\r\n" +
				"security or integrity of Confidential Information (each, a “Security Incident”), provided that such notification is\r\n" +
				"not prohibited by legal authorities. Licensor will investigate and conduct a root cause analysis on the Security\r\n" +
				"Incident and take all reasonable steps to prevent further compromise, access, alteration, loss, use or disclosure of\r\n" +
				"such Confidential Information. Licensor will provide Buyer written details and regular updates regarding\r\n" +
				"Licensor’s internal investigation of each Security Incident, and Licensor will cooperate and work together with\r\n" +
				"Buyer to formulate and execute a plan to rectify all Security Incidents.\r\n" );
				
				Paragraph limitation = new Paragraph();
				limitation.add(
				"3.2 Licensor shall be responsible for all its costs related to or arising from any Security Incident,\r\n" +
			"	including without limitation investigating the Security Incident. At Buyer’s request and cost, Licensor will\r\n" +
			"	reasonably cooperate with Buyer, at Licensor’s expense, in complying with its obligations under applicable law\r\n" +
			"	pertaining to responding to a Security Incident.\r\n");
				
				Paragraph obligation = new Paragraph();
				obligation.add(
				"3.3 Licensor’s obligation to report or respond to a Security Incident under this Section is not an\r\n" +
				"acknowledgement by Licensor of any fault or liability with respect to the Security Incident. Buyer must notify\r\n" +
				"Licensor promptly about any possible misuse of its accounts or authentication credentials or any security incident\r\n" +
				"related to the SaaS Service.\r\n" );
				
				Header data1 = new Header();
				data1.add("4. General.");
				
				
				Paragraph interest = new Paragraph();
				interest.add(
				"4.1 Buyer shall retain ownership of Confidential Information. Licensor shall not obtain any\r\n" +
				"ownership interest in Confidential Information.\r\n");
				
				Paragraph Addendum11 = new Paragraph();
				Addendum11.add(
			"	4.2 Licensor shall not retain Confidential Information beyond the expiration or termination of the\r\n" +
			"	Standard Contract, except as provided in this Security Addendum, the Standard Contract or by law. Upon\r\n" +
			"	completion of the Services, Confidential Information shall be promptly returned, deleted or destroyed as required\r\n" +
			"	under the Standard Contract. If Licensor cannot promptly return, deleted or destroy Confidential Information,\r\n" +
			"	Licensor shall protect such Confidential Information in accordance with this Security Addendum for so long as\r\n" +
			"	Licensor retains such Confidential Information.\r\n" );
				
				Paragraph compliance1 = new Paragraph();
				compliance1.add(
			"	4.3 If Licensor subcontracts its obligations under this Security Addendum, Licensor shall enter into a\r\n" +
			"	written agreement with its subcontractor that (i) imposes in all materials respects the same obligations on the\r\n" +
			"	subcontractor that are imposed on Licensor under this Security Addendum (“Subcontractor Obligations”), and\r\n" +
			"	(ii) does not allow further subcontracting of its obligations. Without limiting the foregoing, Licensor shall remain\r\n" +
			"	liable to Buyer for its obligations under this Security Addendum, including any misuse or mishandling of\r\n" +
			"	Confidential Information by its subcontractors. Licensor will be responsible for the compliance of the\r\n" +
			"	subcontractors with the terms of this Addendum.\r\n" );
				
				Paragraph regulations = new Paragraph();
				regulations.add(
				"4.4 Licensor shall comply with and shall cause each of its subcontractors to comply with all\r\n" +
				"applicable laws and regulations including all data protection and security laws and regulations whether in effect at\r\n" +
				"the time of execution of this Security Addendum or coming into effect thereafter. This Security Addendum does\r\n" +
				"not limit other obligations of Licensor, including under any Laws that apply to Licensor or its performance under\r\n" +
				"this Agreement.\r\n");
				
				Paragraph herein = new Paragraph();
				herein.add(
				"4.5 This Security Addendum and all provisions herein shall survive as long as Licensor and/or\r\n" +
				"subcontractor retains any Confidential Information.\r\n" );
				
				Header data = new Header();
				data.add("General Data Protection Regulation Data Processing Addendum for	Standard Contract for AWS Marketplace (European Economic Area & Switzerland");
				
				Paragraph Economic = new Paragraph();
						Economic.add(
				"This Data Processing Addendum (this “Addendum”) is part of the Standard Contract for AWS Marketplace (the\r\n" +
				"“Standard Contract”) between Licensor (who is the data processor) and Buyer (who is the data controller) and\r\n" +
				"governs Licensor’s processing of Personal Data to the extent such Personal Data relates to natural persons in the\r\n" +
				"European Economic Area or Switzerland in connection with Licensor’s provision of the Services it provides\r\n" +
				"pursuant to the Standard Contract. All capitalized terms used but not defined in this Addendum have the\r\n" +
				"meanings given to them in the Standard Contract.\r\n");
				
				 
				Header data11 = new Header();
				data11.add("Processing of Personal Data");
				

				Paragraph Instructions = new Paragraph();
				Instructions.add(
				"1. Instructions from the Controller. Notwithstanding anything in the Standard Contract to the contrary,\r\n" +
				"Licensor will only process Personal Data in order to provide the Services to Buyer, in accordance with Buyer’s\r\n" +
				"written instructions, or as required by applicable Law. Licensor will promptly inform Buyer if following Buyer\r\n" +
				"instructions would result in a violation of applicable data protection law or where Licensor must disclose Personal\r\n" +
				"Data in response to a legal obligation (unless the legal obligation prohibits Licensor from making such\r\n" +
				"disclosure).\r\n");
						

				Paragraph Confidentiality = new Paragraph();
				Confidentiality.add(
			"	2. Confidentiality. Licensor will restrict access to Personal Data to those authorized persons who need such\r\n" +
			"	information to provide the Services. Such authorized persons are obligated to maintain the confidentiality of any\r\n" +
			"	Personal Data.\r\n" );
								

				Paragraph Sensitive = new Paragraph();
				Sensitive.add(
			"	3. Sensitive Information. Buyer will inform Licensor if Personal Data falls into any special categories of\r\n" );

				Paragraph requirements = new Paragraph();
			requirements.add(
			"	4. Security. Licensor will implement appropriate technical and organizational measures to ensure a level of\r\n" +
			"	security appropriate to the Personal Data provided by Buyer and processed by Licensor. Such security measures\r\n" +
			"	will be at least as protective as the security requirements set forth in Section 8.5 of the Standard Contract.\r\n" );
			

				Paragraph processors = new Paragraph();
			processors.add(
			"	5. Sub-processors. Buyer agrees that Licensor, a processor, may engage other processors (“Sub-\r\n" +
			"	processors”) to assist in providing the Services consistent with the Standard Contract. Licensor will make a list\r\n" +
			"	of such Sub-processors available to Buyer prior to transferring any Personal Data to such Sub-processors.\r\n" +
			"	Licensor will notify Buyer of any changes to the list of Sub-processors in order to give Buyer an opportunity to\r\n" +
			"	object to such changes.\r\n" );
						

				Paragraph Liability = new Paragraph();
				Liability.add(		
				"6. Sub-processor Liability. Where Licensor engages another processor for carrying out specific processing\r\n" +
				"activities on behalf of Buyer, the same data protection obligations as set out in this Addendum will be imposed on\r\n" +
				"that other processor by way of a contract or other legal act under EU or Member State law, in particular providing\r\n" +
				"sufficient guarantees to implement appropriate technical and organizational measures in such a manner that the\r\n" +
				"processing will meet the requirements of the EU data protection law. Where that other processor fails to fulfil its\r\n" +
				"data protection obligations, Licensor shall remain fully liable to the Buyer for the performance of that other\r\n" +
				"processor’s obligations.\r\n" );
								

				Paragraph technical = new Paragraph();
				technical.add(				
				"7. Access Requests. Licensor has implemented and will maintain appropriate technical and organizational\r\n" +
				"measures needed to enable Buyer to respond to requests from data subjects to access, correct, transmit, limit\r\n" +
				"processing of, or delete any relevant Personal Data held by Licensor.\r\n" );

				Paragraph Recordkeeping = new Paragraph();
				Recordkeeping.add(					
				"8. Recordkeeping. Upon a request issued by a supervisory authority for records regarding Personal Data,\r\n" +
				"Licensor will cooperate to provide the supervisory authority with records related to processing activities\r\n" +
				"performed on Buyer’s behalf, including information on the categories of Personal Data processed and the\r\n" +
				"purposes of the processing, the use of service providers with respect to such processing, any data disclosures or\r\n" +
				"transfers to third parties and a general description of technical and organizational measures to protect the security\r\n" +
				"of such data.\r\n" );

				Paragraph Cooperation = new Paragraph();
				Cooperation.add(
				"9. Cooperation. Licensor will cooperate to the extent reasonably necessary in connection with Buyer’s\r\n" +
				"requests related to data protection impact assessments and consultation with supervisory authorities and for the\r\n" +
				"fulfillment of Buyer’s obligation to respond to requests for exercising a data subject’s rights in Chapter III of\r\n" +
				"Regulation (EU) 2016/679. Licensor reserves the right to charge Buyer for its reasonable costs in collecting and\r\n" +
				"preparing Personal Data for transfer and for any special arrangements for making the transfer.\r\n" );

				Paragraph Requests = new Paragraph();
				Requests.add(
				"10. Third Party Requests. If Licensor receives a request from a third party in connection with any\r\n" +
				"government investigation or court proceeding that Licensor believes would require it to produce any Personal\r\n" +
				"Data, Licensor will inform Buyer in writing of such request and cooperate with Buyer if Buyer wishes to limit,\r\n" +
				"challenge or protect against such disclosure, to the extent permitted by applicable Law.\r\n" );

				Paragraph Personal = new Paragraph();
				Personal.add(
				"11. Transfer of Personal Data; Appointment. Buyer authorizes Licensor to transfer, store or process\r\n" +
				"Personal Data in the United States or any other country in which Licensor or its Sub-processors maintain\r\n" +
				"facilities. Buyer appoints Licensor to perform any such transfer of Personal Data to any such country and to store\r\n" +
				"and process Personal Data in order to provide the Services. Licensor will conduct all such activity in compliance\r\n" +
				"with the Standard Contract, this Addendum, applicable Law and Buyer instructions.\r\n" );

				Paragraph Retention = new Paragraph();
				Retention.add(
				"12. Retention. Personal Data received from Buyer will be retained only for so long as may be reasonably\r\n" +
				"required in connection with Licensor’s performance of the Standard Contract or as otherwise required under\r\n" +
				"applicable Law.\r\n" );

				Paragraph Deletion = new Paragraph();
				Deletion.add(
				"13. Deletion or Return. When instructed by Buyer, Licensor will delete any Personal Data or return it to\r\n" +
				"Buyer in a secure manner and delete all remaining copies of Personal Data after such return except where\r\n" +
				"otherwise required under applicable Law. Licensor will relay Buyer’s instructions to all Sub-processors.\r\n" );
				

				Paragraph Notification = new Paragraph();
				Notification.add(
				"14. Breach Notification. After becoming aware of a Personal Data breach, Licensor will notify Buyer\r\n" +
				"without undue delay of: (a) the nature of the data breach; (b) the number and categories of data subjects and data\r\n" +
			"	records affected; and (c) the name and contact details for the relevant contact person at Licensor.\r\n");

				Paragraph Audits = new Paragraph();
				Audits.add(
				"15. Audits. Upon request, Licensor will make available to Buyer all information necessary, and allow for\r\n" +
				"and contribute to audits, including inspections, conducted by Buyer or another auditor mandated by Buyer, to\r\n" +
				"demonstrate compliance with Article 28 of Regulation (EU) 2016/679. For clarity, such audits or inspections are\r\n" +
				"limited to Licensor’s processing of Personal Data only, not any other aspect of Licensor’s business or information\r\n" +
				"systems. If Buyer requires Licensor to contribute to audits or inspections that are necessary to demonstrate\r\n" +
				"compliance, Buyer will provide Licensor with written notice at least 60 days in advance of such audit or\r\n" +
				"inspection. Such written notice will specify the things, people, places or documents to be made available. Such\r\n" +
				"written notice, and anything produced in response to it (including any derivative work product such as notes of\r\n" +
				"interviews), will be considered Confidential Information and, notwithstanding anything to the contrary in the\r\n" +
				"Standard Contract, will remain Confidential Information in perpetuity or the longest time allowable by applicable\r\n" +
				"Law after termination of the Standard Contract. Such materials and derivative work product produced in response\r\n" +
				"to Buyer’s request will not be disclosed to anyone without the prior written permission of Licensor unless such\r\n" +
				"disclosure is required by applicable Law. If disclosure is required by applicable Law, Buyer will give Licensor\r\n" +
				"prompt written notice of that requirement and an opportunity to obtain a protective order to prohibit or restrict\r\n" +
				"such disclosure except to the extent such notice is prohibited by applicable Law or order of a court or\r\n" +
				"governmental agency. Buyer will make every effort to cooperate with Licensor to schedule audits or inspections\r\n" +
				"at times that are convenient to Licensor. If, after reviewing Licensor’s response to Buyer’s audit or inspection\r\n" +
				"request, Buyer requires additional audits or inspections, Buyer acknowledges and agrees that it will be solely\r\n" +
				"responsible for all costs incurred in relation to such additional audits or inspections\r\n" );
				
	    add(head,scope,termsandconditions,softwaresubp,taxes,agreement,License,Licenses,Licensor,connection,reasonable,deemed,Restrictions,reverse,Additional,Activities,serviceheader,Saa_Service,support_services,feedback,warrenty,warranties,
	    		services,remedies,special_remedy,warranty_exclusions,compliance1,Power,disclaimer,confidentiality,confidential,obligations,
	    		compelleddisclosure,buyer,saas,acceptable_use,buyer_materials,authorization
	    		,information,entirety,conjunction,Notwithstanding,security,protection,Legislation,personal_information,remedy,limitations,Disclaimer,
	    		Exception,Indemnification,Breach,Breach1,Breach2,Indemnification1,Buyer_Indemnity,Process,Infringement
	    		,unauthorized,Limitations,liability,Claim,
	    		infringement,indemnities,Termination,conclusion,Convenience,breaches,expiration,request,Proprietary,Insurance,Commercial,Commercial
	    		,Coverage,insurance,evidencing,General,Applicable,Assignment,Divestiture,amendment,Majeure,Commerce,commercial,Headings,Beneficiaries,
	    		Notices,Nonwaiver,Publicity,Relationship,Severability,omission,definitition,Affiliate
	    		,provisioned,functionality,marketplace1,computing,Confidential,furnished,contractor,
	    		modifications,Entitlement,materials,Pricing,Personnel1,Addendum11,Proprietary1,infrastructure,vulnerabilities,Contract,subcontractor,Marketplace
	    		,Support,System,employee,Period,label1,label2,label3,accidental,administrative,business,prohibition,evaluation,duties,Addendum1,Access,disclose,environment,segregation,stored,separation,passwords,authentication,policies,Personnel1,industry,standards,enforce,accounts,safeguards,manner,Continuity,Personal1,
	    		acknowledges,practicable,relation,Incidents,limitation,obligation,data11,interest,Addendum11,compliance1,regulations,herein,data11,Economic,data11,Instructions,Confidentiality,
	    		Sensitive,requirements,processors,Liability,technical,Recordkeeping,Cooperation,Requests,Personal,Retention,Deletion,Notification,Audits);
	   
		// this.SetMessage("Are you sure, you want to close the project without
		// saving?");
		
	}
	private void SetUI() {
		// TODO Auto-generated method stub
	
	}
	

}
